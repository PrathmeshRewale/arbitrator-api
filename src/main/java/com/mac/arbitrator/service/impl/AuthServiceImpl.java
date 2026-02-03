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
import java.util.Optional;

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
    private final ArbitratorUserRepository arbitratorUserRepository;

    public AuthServiceImpl(UserRepository userRepository, UserOtpRepository userOtpRepository, AuthenticationManager authenticationManager, EmailService emailService, JwtUtil jwtUtil, RoleService roleService, PasswordEncoder passwordEncoder, AdmissionFormClaimantRepository admissionFormClaimantRepository, AdmissionFormRespondantRepository admissionFormRespondantRepository, AdmissionFormUserRepository admissionFormUserRepository, ArbitratorUserRepository arbitratorUserRepository) {
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
        this.arbitratorUserRepository = arbitratorUserRepository;
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
        Role role = roleService.getRoleByName("USER");

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

    // ---------------- LOGIN ----------------

    @Override
    public GenericResponseDto login(LoginRequestRequestDto dto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
                );

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        int otp = generateAndSaveOtp(user.getId());

        emailService.sendSystemMail(
                new CreateEmailRequestDto(user.getEmail(),
                        MailTemplate.generateLoginOtpEmail(user.getFullName(), otp))
        );

        return new GenericResponseDto("success", "OTP sent");
    }

    // ---------------- VERIFY LOGIN OTP ----------------

    @Override
    public LoginResponseDto verifyLoginOtp(VerifyOtpRequestDto dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserOtp otp = userOtpRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.getOtp().equals(dto.getOtp()))
            throw new RuntimeException("Invalid OTP");

        if (otp.getExpired().isBefore(Instant.now()))
            throw new RuntimeException("OTP expired");

        userOtpRepository.deleteByUserId(user.getId());

        ArbitratorUser arbitratorUser = arbitratorUserRepository
                .findByUserId(user.getId())
                .orElse(null);

        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        Role role = roleService.getById(user.getRoleId());

        LoginResponseDto res = new LoginResponseDto();
        res.setUserId(user.getId());
        res.setUserEmail(user.getEmail());
        res.setUsername(user.getUsername());
        if(arbitratorUser != null) {
            res.setArbitratorId(arbitratorUser.getArbitratorId());
        }
        if(arbitratorUser == null){
            res.setArbitratorId(0l);
        }
        res.setRoleId(user.getRoleId());
        res.setRoleName(user.getRoleName());
        res.setPermissions(role.getPermissions());
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);

        return res;
    }

    // ---------------- FORGOT PASSWORD ----------------

    @Override
    public GenericResponseDto forgotPassword(ForgotPasswordRequestDto dto) {

        User user = userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getUserEmail());

        System.out.println(user);

        if (user == null)
            throw new RuntimeException("User not found");

        int otp = generateAndSaveOtp(user.getId());

        emailService.sendSystemMail(
                new CreateEmailRequestDto(user.getEmail(),
                        MailTemplate.generateForgotPasswordOtpEmail(user.getFullName(), otp))
        );

        return new GenericResponseDto("success", "OTP sent");
    }

    // ---------------- VERIFY FORGOT OTP ----------------

    @Override
    public GenericResponseDto verifyForgotPasswordOtp(VerifyOtpRequestDto dto) {

        User user = userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getUserEmail());

        if (user == null)
            throw new RuntimeException("User not found");

        UserOtp otp = userOtpRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.getOtp().equals(dto.getOtp()))
            throw new RuntimeException("Invalid OTP");

        if (otp.getExpired().isBefore(Instant.now()))
            throw new RuntimeException("OTP expired");

        otp.setIsVerified(true);
        userOtpRepository.save(otp);

        return new GenericResponseDto("success", "OTP verified");
    }

    // ---------------- UPDATE PASSWORD ----------------

    @Override
    public GenericResponseDto updateUserPassword(UpdatedUserPasswordRequestDto dto) {

        User user = userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getUserEmail());

        if (user == null)
            throw new RuntimeException("User not found");

        UserOtp otp = userOtpRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.getIsVerified())
            throw new RuntimeException("OTP not verified");

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        userOtpRepository.deleteByUserId(user.getId());

        return new GenericResponseDto("success", "Password updated");
    }

    // ---------------- OTP GENERATION ----------------

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
        int min = (int) Math.pow(10, length - 1); // 100000
        int max = (int) Math.pow(10, length) - 1; // 999999
        return random.nextInt(max - min + 1) + min;
    }

}
