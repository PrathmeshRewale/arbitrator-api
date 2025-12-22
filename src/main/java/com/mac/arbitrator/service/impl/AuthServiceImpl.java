package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.EmailRequest;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.dto.response.LoginResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.exception.UserNotFoundException;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.security.JwtTokenProvider;
import com.mac.arbitrator.service.AuthService;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserAdmissionFormRepository userAdmissionFormRepository;
    private final ClaimantRepository claimantRepository;
    private final RespondantRepository respondantRepository;
    private final RoleRepository roleRepository;
    private final OtpService otpService;
    private final EmailService emailService;

    @Override
    public GenericResponseDto createUser(CreateSignupRequest createSignupRequest) {
        // Check if user already exists (by username/email/phone)

        if (userRepository.existsByUsername(createSignupRequest.username())) {
            return new GenericResponseDto("error", "Username already exists");
        }

        if (userRepository.existsByEmail(createSignupRequest.email())) {
            return new GenericResponseDto("error", "Email already in use");
        }

        if (userRepository.existsByPhoneNo(createSignupRequest.phoneNo())) {
            return new GenericResponseDto("error", "Phone number already in use");
        }

        // Fetch admission form
        Optional<AdmissionForm> admissionFormOpt = admissionFormRepository.findById(createSignupRequest.admissionId());

        if (admissionFormOpt.isEmpty()) {
            return new GenericResponseDto("error", "Admission request not found");
        }

        AdmissionForm admissionForm = admissionFormOpt.get();

        if (!"APPROVED".equalsIgnoreCase(admissionForm.getStatus().toString())) {
            return new GenericResponseDto("error", "Your admission request is not yet approved");
        }

//        Payment payment = paymentRepository.findByUserEmailAndAdmissionId(createSignupRequest.email(),createSignupRequest.admissionId()).orElseThrow(()->new UserNotFoundException(HttpStatus.NOT_FOUND,"Payment not found by the email id"));
//
//        if (PaymentStatus.UNPAID.equals(payment.getPaymentStatus())){
//            return new GenericResponseDto("error", "Your payment is not yet made");
//        }

        // Get claimants and respondents by admission ID
        List<Claimant> claimants = claimantRepository.findByAdmissionFormId(createSignupRequest.admissionId());
        List<Respondant> respondants = respondantRepository.findByAdmissionFormId(createSignupRequest.admissionId());

        // Determine user role
        Long roleId;
        UserCaseType userCaseType = null;
        Optional<Role> userRole = roleRepository.findByName("USER");

        if (claimants.stream().anyMatch(claimant -> claimant.getEmail().equalsIgnoreCase(createSignupRequest.email()))) {
            roleId = userRole.orElseThrow(() -> new RuntimeException("Claimant role not found")).getId();
            userCaseType = UserCaseType.CLAIMANT;
            } else if (respondants.stream().anyMatch(respondant -> respondant.getEmail().equalsIgnoreCase(createSignupRequest.email()))) {
                roleId = userRole.orElseThrow(() -> new RuntimeException("Respondent role not found")).getId();
                userCaseType = UserCaseType.RESPONDANT;
            } else {
                return new GenericResponseDto("error", "You are not associated with this admission form");
            }

            // Create user and save
            User user = mapDtoToUser(createSignupRequest, roleId);
            user = userRepository.save(user);

            // Link user with admission form
            UserAdmissionForm admissionForm1 = new UserAdmissionForm();
            admissionForm1.setUserAdmissionFormEmbeddable(new UserAdmissionFormEmbeddable(user.getId(), createSignupRequest.admissionId(),userCaseType));
            userAdmissionFormRepository.saveAndFlush(admissionForm1);

            return new GenericResponseDto("success", "User registered successfully");
        }


    @Override
    public LoginResponseDto loginUser(CreateLoginRequest createLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(createLoginRequest.username(), createLoginRequest.password())
        );

        User user = userRepository.findByUsername(createLoginRequest.username())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND,"User not found"));

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND,"Role not found"));

        //SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(user);

            LoginResponseDto responseDto = new LoginResponseDto();
            responseDto.setToken(token);

        //Users user = userRepository.findByUsername(authentication.getName()).orElseThrow();
            responseDto.setUsername(user.getUsername());
            responseDto.setUserId(user.getId());
            responseDto.setUserEmail(user.getEmail());
            responseDto.setRoleName(role.getName());
            responseDto.setRoleId(user.getRoleId());
            responseDto.setPermissions(mapToRoleResponse(role.getPermissions()));
            return  responseDto;
    }

    @Override
    public GenericResponseDto forgotPasswordRequest(CreateForgotPasswordRequest createForgotPasswordRequest) {
        User user = userRepository.findByUsernameOrEmail(createForgotPasswordRequest.userName(),createForgotPasswordRequest.userEmail());
        Integer otp = otpService.generateAndSaveOtp(user.getId());
        String messagebody = EmailServiceImpl.generateForgotPasswordOtpEmail(user.getFullName(),otp);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .msgBody(messagebody)
                .build();
        emailService.sendUserMail(emailRequest);
        return new GenericResponseDto("success",user.getId().toString());
    }

    @Override
    public GenericResponseDto forgotPasswordResendRequest(CreateForgotPasswordRequest createForgotPasswordRequest) {
        User user = userRepository.findByUsernameOrEmail(createForgotPasswordRequest.userName(),createForgotPasswordRequest.userEmail());
        Integer otp = otpService.resendOtp(user.getId());
        String messagebody = EmailServiceImpl.generateForgotPasswordOtpEmail(user.getFullName(),otp);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .msgBody(messagebody)
                .build();
        emailService.sendUserMail(emailRequest);
        return new GenericResponseDto("success",user.getId().toString());
    }

    @Override
    public GenericResponseDto verifyOtp(CreateForgotPasswordVerifyRequest createForgotPasswordVerifyRequest) {
        return otpService.verifyOtp(createForgotPasswordVerifyRequest.userid(),createForgotPasswordVerifyRequest.otp());
    }

    @Override
    public GenericResponseDto updateUserPassword(CreateNewPasswordRequest createNewPasswordRequest) {

        boolean isOtpVarified = otpService.isOtpVerified(createNewPasswordRequest.userId());

        if(!isOtpVarified){
            return new GenericResponseDto("error","Otp is not varified");
        }

        User user = userRepository.findById(createNewPasswordRequest.userId()).orElseThrow(()->new RuntimeException("user with id -> " + createNewPasswordRequest.userId() + " not found"));

        user.setPassword(passwordEncoder.encode(createNewPasswordRequest.newPassword()));

        userRepository.save(user);

        return new GenericResponseDto("success","password updated successfully");

    }

    User mapDtoToUser(CreateSignupRequest createSignupRequest, Long role){
        return User.builder()
                .email(createSignupRequest.email())
                .fullName(createSignupRequest.fullName())
                .phoneNo(createSignupRequest.phoneNo())
                .roleId(role)
                .password(passwordEncoder.encode(createSignupRequest.password()))
                .username(createSignupRequest.username())
                .build();
    }

    public Map<String, Object> mapToRoleResponse(String permissions){
        Map<String, Object> permissionMap = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            permissionMap = objectMapper.readValue(permissions, Map.class);
        } catch (JsonMappingException e) {
//                throw new RuntimeException(e);
            permissionMap = Map.of("error", "invalid JSON");
        } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
            permissionMap = Map.of("error", "Invalid JSON");
        }
        return  permissionMap;
    }
}
