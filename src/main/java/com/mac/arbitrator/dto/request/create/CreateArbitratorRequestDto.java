package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateArbitratorRequestDto {

    // Arbitrator personal details
    private String fullName;
    private String barRegistrationNumber;
    private String email;
    private Gender gender;
    private String phoneNumber;
    private LocalDate enrollmentDate;

    // Jurisdiction details
    private Long jurisdictionId;
    private String jurisdictionName;

    // Audit fields (who is creating)
    private Long createdById;
    private String createdByName;
}
