package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateArbitratorRequestDto {

    // Arbitrator editable details
    private String fullName;
    private String barRegistrationNumber;
    private String email;
    private Gender gender;
    private String phoneNumber;
    private LocalDate enrollmentDate;

    // Jurisdiction details
    private Long jurisdictionId;
    private String jurisdictionName;

    // Audit fields (who updated)
    private Long updatedById;
    private String updatedByName;
}
