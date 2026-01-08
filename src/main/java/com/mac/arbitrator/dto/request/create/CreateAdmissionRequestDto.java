package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdmissionRequestDto {

    private String defaultClause;

    private Long jurisdictionId;

    private String jurisdictionName;

    private String arbitrationClause;

    private String refiefSought;

    private Float disputeAmount;

    private LocalDate disputeDate;

    private List<CreateAdmissionClaimantRequestDto> claimants;

    private List<CreateAdmissionRespondantRequestDto> respondants;

    private CreateAdmissionDocumentsRequestDto documents;

    private String status;
}
