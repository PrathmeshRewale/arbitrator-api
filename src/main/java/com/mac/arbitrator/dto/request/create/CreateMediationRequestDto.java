package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMediationRequestDto {

    private String defaultClause;

    private Long jurisdictionId;

    private String jurisdictionName;

    private String arbitrationClause;

    private String refiefSought;

    private Float disputeAmount;

    private LocalDate disputeDate;

    private List<CreateMediationClaimantRequestDto> claimants;

    private List<CreateMediationRespondantRequestDto> respondants;

    private CreateMediationDocumentsRequestDto documents;

    private String status;
}
