package com.mac.arbitrator.dto.request.create;

public record CreateUserRequest(
        String username,
        String password,
        String fullName,
        String email,
        String phoneNo,
        Long roleId,
        Long createdById,
        String createdByName
) {
}
