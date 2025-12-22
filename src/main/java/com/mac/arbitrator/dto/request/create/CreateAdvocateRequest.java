package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.Gender;

import java.time.Instant;

public record CreateAdvocateRequest(
        String username,
        String password,
        String fullName,
        String barRegistrationNumber,
        String email,
        String phoneNumber,
        Instant enrollmentDate,
        Gender gender,
        Long jurisdictionId,
        String jurisdictionName,
        Long createdById,
        String createdByName
) {
}
