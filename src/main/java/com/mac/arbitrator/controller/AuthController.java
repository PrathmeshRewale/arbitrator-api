package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.dto.response.LoginResponseDto;
import com.mac.arbitrator.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/apiv1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> createUser(@RequestBody CreateSignupRequest createSignupRequest) {
        return new ResponseEntity<GenericResponseDto>(authService.createUser(createSignupRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody CreateLoginRequest loginRequest) {
        return new ResponseEntity<LoginResponseDto>(authService.loginUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/forgot_password_request")
    public ResponseEntity<?> forgotPasswordRequest(@RequestBody CreateForgotPasswordRequest createForgotPasswordRequest) {
        return new ResponseEntity<GenericResponseDto>(authService.forgotPasswordRequest(createForgotPasswordRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/resend_forgot_password_request")
    public ResponseEntity<?> resendForgotPasswordRequest(@RequestBody CreateForgotPasswordRequest createForgotPasswordRequest) {
        return new ResponseEntity<GenericResponseDto>(authService.forgotPasswordResendRequest(createForgotPasswordRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/verify_otp")
    public ResponseEntity<?> verifyForgotPasswordRequest(@RequestBody CreateForgotPasswordVerifyRequest createForgotPasswordVerifyRequest) {
        return new ResponseEntity<GenericResponseDto>(authService.verifyOtp(createForgotPasswordVerifyRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/add_new_password")
    public ResponseEntity<?> addNewPassword(@RequestBody CreateNewPasswordRequest createNewPasswordRequest) {
        return new ResponseEntity<GenericResponseDto>(authService.updateUserPassword(createNewPasswordRequest), HttpStatus.OK);
    }
}
