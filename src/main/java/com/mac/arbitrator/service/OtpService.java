package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;

public interface OtpService {
    Integer generateAndSaveOtp(Long userId);
    GenericResponseDto verifyOtp(Long userId,Integer otp);
    Integer resendOtp(Long userId);
    boolean isOtpVerified(Long userId);
    void deleteAllOtpByUserId(Long userId);
}
