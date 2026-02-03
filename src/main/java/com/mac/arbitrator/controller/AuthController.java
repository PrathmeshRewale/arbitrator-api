package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.ForgotPasswordRequestDto;
import com.mac.arbitrator.dto.request.LoginRequestRequestDto;
import com.mac.arbitrator.dto.request.VerifyOtpRequestDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserRequestDto;
import com.mac.arbitrator.dto.request.update.UpdatedUserPasswordRequestDto;
import com.mac.arbitrator.dto.response.LoginResponseDto;
import com.mac.arbitrator.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestRequestDto loginResponseDto){
        System.out.println(loginResponseDto.getPassword()+"<-password,username->"+loginResponseDto.getUsername());
        return ResponseEntity.ok(authService.login(loginResponseDto));
    }

    @PostMapping("/verify_login_otp")
    public ResponseEntity<?> verifyLoginOtp(@RequestBody VerifyOtpRequestDto verifyOtpRequestDto){
        return ResponseEntity.ok(authService.verifyLoginOtp(verifyOtpRequestDto));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto){
        return ResponseEntity.ok(authService.forgotPassword(forgotPasswordRequestDto));
    }

    @PostMapping("/verify_forgot_password_otp")
    public ResponseEntity<?> verifyForgotPasswordOtp(@RequestBody VerifyOtpRequestDto verifyOtpRequestDto){
        return ResponseEntity.ok(authService.verifyForgotPasswordOtp(verifyOtpRequestDto));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@RequestBody UpdatedUserPasswordRequestDto updatedUserPasswordRequestDto){
        return ResponseEntity.ok(authService.updateUserPassword(updatedUserPasswordRequestDto));
    }

    @PostMapping("/register_user")
    public ResponseEntity<?> RegisterUser(@RequestBody CreateAdmissionFormUserRequestDto createAdmissionFormUserRequestDto){
        return ResponseEntity.ok(authService.registerAdmissionFormUser(createAdmissionFormUserRequestDto));
    }
}
