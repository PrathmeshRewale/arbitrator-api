package com.mac.arbitrator.dto.request.create;

public record CreateNewPasswordRequest(
        Long userId,
        String newPassword
) {
}
