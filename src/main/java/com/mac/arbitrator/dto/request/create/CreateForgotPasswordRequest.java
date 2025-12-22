package com.mac.arbitrator.dto.request.create;

public record CreateForgotPasswordRequest(
        String userName,
        String userEmail
) {
}
