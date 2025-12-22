package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.Gender;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvocateResponseDto {
    private Long id;
    private String username;
    private String password;
    private Long roleId;
    private String fullName;
    private String barRegistrationNumber;
    private String email;
    private String phoneNumber;
    private Instant enrollmentDate;
    private Gender gender;
    private Long jurisdictionId;
    private String jurisdictionName;
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;
}