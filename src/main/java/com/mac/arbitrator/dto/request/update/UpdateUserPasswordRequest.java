package com.mac.arbitrator.dto.request.update;

public record UpdateUserPasswordRequest(
        Long id,
        String password,
        Long updatedById,
        String updatedByName
) {
}
