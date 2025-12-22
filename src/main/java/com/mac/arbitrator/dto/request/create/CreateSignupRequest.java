package com.mac.arbitrator.dto.request.create;

public record CreateSignupRequest(
        String username,
        String password,
        String fullName,
        String email,
        String phoneNo,
        Long admissionId
) {
}
