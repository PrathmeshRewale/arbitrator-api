package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.Gender;

import java.time.Instant;

public record UpdateAdvocateRequest(
        String fullName,
        String barRegistrationNumber,
        String email,
        String phoneNumber,
        Instant enrollmentDate,
        Gender gender,
        Long jurisdictionId,
        String jurisdictionName,
        Long updatedById,
        String updatedByName
) {
}
