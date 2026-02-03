package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArbitratorResponseDto {
    private Long id;
    private String fullName;
    private String barRegistrationNumber;
    private String email;
    private Gender gender;
    private String phoneNumber;
    private LocalDate enrollmentDate;
    private Long jurisdictionId;
    private String jurisdictionName;
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;
}
