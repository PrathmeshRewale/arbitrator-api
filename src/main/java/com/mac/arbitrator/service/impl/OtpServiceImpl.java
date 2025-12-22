package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.entity.Otp;
import com.mac.arbitrator.repository.OtpRepository;
import com.mac.arbitrator.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final long OTP_EXPIRY_DURATION_MS = 15 * 60 * 1000;
    private static final int OTP_LENGTH = 6;
    private static final String OTP_CHARACTERS = "0123456789";

    private final OtpRepository otpRepository;


    @Override
    public Integer generateAndSaveOtp(Long userId) {
        otpRepository.deleteAllByUserId(userId);
        Integer otp = generateNumericOtp(OTP_LENGTH);
        Otp otp1 = Otp.builder()
                .otp(otp)
                .userId(userId)
                .expired(Instant.now().plusMillis(OTP_EXPIRY_DURATION_MS))
                .isVerified(false)
                .build();
        otpRepository.save(otp1);

        return otp;
    }

    @Override
    public GenericResponseDto verifyOtp(Long userId, Integer otp) {
        boolean isValid = otpRepository.existsByUserIdAndOtp(userId,otp);
        if(isValid){
            Otp otp1 = otpRepository.findByUserId(userId);
            boolean isExpired = Instant.now().isAfter(otp1.getExpired());
            if(isExpired){
                return new GenericResponseDto("error","Otp expired");
            }
            otp1.setIsVerified(true);
            otpRepository.save(otp1);
            return new GenericResponseDto("success","Otp verified successfully");
        }else{
            return new GenericResponseDto("error","Invalid Otp");
        }
    }

    @Override
    public Integer resendOtp(Long userId) {
        Otp otp = otpRepository.findByUserId(userId);
        Integer otp1 = generateNumericOtp(OTP_LENGTH);
        otp.setOtp(otp1);
        otp.setExpired(Instant.now().plusMillis(OTP_EXPIRY_DURATION_MS));
        otpRepository.save(otp);
        return otp1;
    }

    @Override
    public boolean isOtpVerified(Long userId) {
        Otp otp = otpRepository.findByUserId(userId);
        return otp.getIsVerified();
    }

    @Override
    public void deleteAllOtpByUserId(Long userId) {
        otpRepository.deleteAllByUserId(userId);
    }

    private Integer generateNumericOtp(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otpBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            otpBuilder.append(OTP_CHARACTERS.charAt(random.nextInt(OTP_CHARACTERS.length())));
        }

        return Integer.valueOf(otpBuilder.toString());
    }

}
