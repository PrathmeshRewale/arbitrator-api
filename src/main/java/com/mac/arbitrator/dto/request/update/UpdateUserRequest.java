package com.mac.arbitrator.dto.request.update;

public record UpdateUserRequest(
        String fullName,
        String email,
        String phoneNo,
        Long roleId,
        Long updatedById,
        String updatedByName
) {
}
