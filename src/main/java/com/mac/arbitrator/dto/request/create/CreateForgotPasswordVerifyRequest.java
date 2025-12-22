package com.mac.arbitrator.dto.request.create;

public record CreateForgotPasswordVerifyRequest(
        Long userid,
        Integer otp
) {
}
