package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.ForgotPasswordRequestDto;
import com.mac.arbitrator.dto.request.LoginRequestRequestDto;
import com.mac.arbitrator.dto.request.VerifyOtpRequestDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserRequestDto;
import com.mac.arbitrator.dto.request.update.UpdatedUserPasswordRequestDto;
import com.mac.arbitrator.dto.response.LoginResponseDto;

public interface AuthService {
    GenericResponseDto login(LoginRequestRequestDto loginRequestRequestDto);
    LoginResponseDto verifyLoginOtp(VerifyOtpRequestDto verifyLoginOtp);
    GenericResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
    GenericResponseDto verifyForgotPasswordOtp(VerifyOtpRequestDto verifyOtpRequestDto);
    GenericResponseDto updateUserPassword(UpdatedUserPasswordRequestDto updatedUserPasswordRequestDto);
    GenericResponseDto registerAdmissionFormUser(CreateAdmissionFormUserRequestDto createAdmissionFormUserRequestDto);
}
