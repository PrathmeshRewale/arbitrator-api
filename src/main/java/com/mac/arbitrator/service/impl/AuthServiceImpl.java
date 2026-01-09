package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.ForgotPasswordRequestDto;
import com.mac.arbitrator.dto.request.LoginRequestRequestDto;
import com.mac.arbitrator.dto.request.VerifyOtpRequestDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserRequestDto;
import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;
import com.mac.arbitrator.dto.request.create.CreateUserRequestDto;
import com.mac.arbitrator.dto.request.update.UpdatedUserPasswordRequestDto;
import com.mac.arbitrator.dto.response.LoginResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.AuthService;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.RoleService;
import com.mac.arbitrator.repository.UserRepository;
import com.mac.arbitrator.service.UserService;
import com.mac.arbitrator.util.JwtUtil;
import com.mac.arbitrator.util.MailTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AdmissionFormClaimantRepository admissionFormClaimantRepository;
    private final AdmissionFormRespondantRepository admissionFormRespondantRepository;
    private final AdmissionFormUserRepository admissionFormUserRepository;

    public AuthServiceImpl(UserRepository userRepository, UserOtpRepository userOtpRepository, AuthenticationManager authenticationManager, EmailService emailService, JwtUtil jwtUtil, RoleService roleService, PasswordEncoder passwordEncoder, AdmissionFormClaimantRepository admissionFormClaimantRepository, AdmissionFormRespondantRepository admissionFormRespondantRepository, AdmissionFormUserRepository admissionFormUserRepository) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.admissionFormClaimantRepository = admissionFormClaimantRepository;
        this.admissionFormRespondantRepository = admissionFormRespondantRepository;
        this.admissionFormUserRepository = admissionFormUserRepository;
    }

    private static final long OTP_EXPIRY_DURATION_MS = 10 * 60 * 1000;
    private static final int OTP_LENGTH = 6;
    private static final String OTP_CHARACTERS = "0123456789";


    @Override
    public GenericResponseDto registerAdmissionFormUser(CreateAdmissionFormUserRequestDto req) {
        //existing user id user mail already existed
        User user1 = userRepository.findByUsernameOrEmail("",req.getUserEmail());
        if(user1 != null){
            return new GenericResponseDto(
                    "error",
                    "User with email already existed"
            );
        }

        List<AdmissionFormClaimant> admissionFormClaimants =
                admissionFormClaimantRepository.findByAdmissionFormId(req.getAdmissionId());

        List<AdmissionFormRespondant> admissionFormRespondants =
                admissionFormRespondantRepository.findByAdmissionFormId(req.getAdmissionId());

        UserCaseType userType = null;
        String secondaryEmail = null;

        // Check in claimants
        for (AdmissionFormClaimant claimant : admissionFormClaimants) {
            if (claimant.getEmail().equalsIgnoreCase(req.getUserEmail())) {
                userType = UserCaseType.CLAIMANT;
                secondaryEmail = claimant.getSecondaryEmail();
                break;
            }
        }

        // If not claimant, check in respondents
        if (userType == null) {
            for (AdmissionFormRespondant respondent : admissionFormRespondants) {
                if (respondent.getEmail().equalsIgnoreCase(req.getUserEmail())) {
                    userType = UserCaseType.RESPONDANT;
                    secondaryEmail = respondent.getSecondaryEmail();
                    break;
                }
            }
        }

        // If user is neither claimant nor respondent
        if (userType == null) {
            return new GenericResponseDto(
                    "error",
                    "You're not associated with this admission form"
            );
        }

        // Assign role based on user type
        Role role;
        if (userType == UserCaseType.CLAIMANT) {
            role = roleService.getRoleByName("CLAIMANT");
        } else {
            role = roleService.getRoleByName("RESPONDENT");
        }

        User createUserRequestDto = new User();
        createUserRequestDto.setEmail(req.getUserEmail());
        createUserRequestDto.setPassword(passwordEncoder.encode(req.getPassword()));
        createUserRequestDto.setAlternativeEmail(secondaryEmail);
        createUserRequestDto.setPhoneNo(req.getPhoneNo());
        createUserRequestDto.setFullName(req.getFullName());
        createUserRequestDto.setUsername(req.getUsername());
        createUserRequestDto.setRoleId(role.getId());
        createUserRequestDto.setRoleName(role.getName());

        User user = userRepository.save(createUserRequestDto);

        AdmissionFormUser admissionFormUser = new AdmissionFormUser();
        admissionFormUser.setAdmissionId(req.getAdmissionId());
        admissionFormUser.setUserId(user.getId());
        admissionFormUser.setUserCaseType(userType);

        admissionFormUserRepository.save(admissionFormUser);

        return new GenericResponseDto("success","record added successfully");
    }


    @Override
    public GenericResponseDto login(LoginRequestRequestDto loginRequestRequestDto) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequestRequestDto.getUsername(),
                                    loginRequestRequestDto.getPassword()
                            )
                    );

            String username = authentication.getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            int otp = generateAndSaveOtp(user.getId());

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();

            String messageBody = MailTemplate.generateLoginOtpEmail(user.getFullName(),otp);

            createEmailRequestDto.setRecipient(user.getEmail());
            createEmailRequestDto.setMsgBody(messageBody);

            emailService.sendSystemMail(createEmailRequestDto);

            return new GenericResponseDto("susses","Otp sent");

        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public LoginResponseDto verifyLoginOtp(VerifyOtpRequestDto verifyLoginOtp) {
        User user = userRepository.findByUsername(verifyLoginOtp.getUsername()).orElseThrow(()->new RuntimeException("User not fount"));
        UserOtp userOtp = userOtpRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("User not found in te otp"));

        if(userOtp.getOtp() != verifyLoginOtp.getOtp()){
            throw new RuntimeException("Invalid Otp");
        }

        if(userOtp.getExpired().isAfter(Instant.now().plusMillis(OTP_EXPIRY_DURATION_MS))){
            throw new RuntimeException("Otp expired");
        }

        userOtpRepository.deleteByUserId(user.getId());

        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        Role role = roleService.getById(user.getRoleId());

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        loginResponseDto.setUserId(user.getId());
        loginResponseDto.setUserEmail(user.getEmail());
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setRoleName(user.getRoleName());
        loginResponseDto.setRoleId(user.getRoleId());
        loginResponseDto.setPermissions(role.getPermissions());
        loginResponseDto.setRefreshToken(refreshToken);
        loginResponseDto.setAccessToken(accessToken);

        return loginResponseDto;
    }

    @Override
    public GenericResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        User user = userRepository.findByUsernameOrEmail(forgotPasswordRequestDto.getUsername(),forgotPasswordRequestDto.getUserEmail());

        Integer otp = generateAndSaveOtp(user.getId());

        CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();

        String messagebody = MailTemplate.generateForgotPasswordOtpEmail(user.getFullName(),otp);

        createEmailRequestDto.setRecipient(user.getEmail());
        createEmailRequestDto.setMsgBody(messagebody);

        emailService.sendSystemMail(createEmailRequestDto);
        return new GenericResponseDto("success","Otp sent");
    }

    @Override
    public GenericResponseDto verifyForgotPasswordOtp(VerifyOtpRequestDto verifyOtpRequestDto) {
        User user = userRepository.findByUsernameOrEmail(verifyOtpRequestDto.getUsername(),verifyOtpRequestDto.getUserEmail());
        UserOtp userOtp = userOtpRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("User not found in te otp"));

        if(userOtp.getOtp() != verifyOtpRequestDto.getOtp()){
            throw new RuntimeException("Invalid Otp");
        }

        if(userOtp.getExpired().isAfter(Instant.now().plusMillis(OTP_EXPIRY_DURATION_MS))){
            throw new RuntimeException("Otp expired");
        }

        userOtp.setIsVerified(true);

        userOtpRepository.save(userOtp);

        return new GenericResponseDto("success","Otp Verified");
    }

    @Override
    public GenericResponseDto updateUserPassword(UpdatedUserPasswordRequestDto updatedUserPasswordRequestDto) {
        User user = userRepository.findByUsernameOrEmail(updatedUserPasswordRequestDto.getUsername(),updatedUserPasswordRequestDto.getUserEmail());
        UserOtp userOtp = userOtpRepository.findByUserId(user.getId()).orElseThrow(()->new RuntimeException("User not found in te otp"));

        if(!userOtp.getIsVerified()){
            throw new RuntimeException("Otp not verified");
        }

        user.setPassword(passwordEncoder.encode(updatedUserPasswordRequestDto.getNewPassword()));
        userRepository.save(user);

        return new GenericResponseDto("success","Password updated successfully");
    }

    @Transactional
    public int generateAndSaveOtp(Long userId){
        userOtpRepository.deleteByUserId(userId);
        UserOtp userOtp = new UserOtp();
        userOtp.setUserId(userId);
        userOtp.setOtp(generateNumericOtp(6));
        userOtp.setExpired(Instant.now().plusMillis(OTP_EXPIRY_DURATION_MS));
        userOtp.setIsVerified(false);
        UserOtp userOtp1 = userOtpRepository.save(userOtp);
        return userOtp1.getOtp();
    }

    private int generateNumericOtp(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otpBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            otpBuilder.append(OTP_CHARACTERS.charAt(random.nextInt(OTP_CHARACTERS.length())));
        }

        return Integer.parseInt(otpBuilder.toString());
    }
}
