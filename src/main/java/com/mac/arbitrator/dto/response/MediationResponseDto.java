package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MediationResponseDto {

    private Long id;

    private String defaultClause;

    private Long jurisdictionId;

    private String jurisdictionName;

    private String arbitrationClause;

    private String refiefSought;

    private Float disputeAmount;

    private LocalDate disputeDate;

    private List<ClaimantResponseDto> claimants;

    private List<RespondantResponseDto> respondants;

    private DocumentsResponseDto documents;

    private String status;

    private LocalDate createdAt;
}
