package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.dto.response.LoginResponseDto;

public interface AuthService {
    GenericResponseDto createUser(CreateSignupRequest createSignupRequest);
    LoginResponseDto loginUser(CreateLoginRequest loginRequest);
    GenericResponseDto forgotPasswordRequest(CreateForgotPasswordRequest createForgotPasswordRequest);
    GenericResponseDto forgotPasswordResendRequest(CreateForgotPasswordRequest createForgotPasswordRequest);
    GenericResponseDto verifyOtp(CreateForgotPasswordVerifyRequest createForgotPasswordVerifyRequest);
    GenericResponseDto updateUserPassword(CreateNewPasswordRequest createNewPasswordRequest);
}
