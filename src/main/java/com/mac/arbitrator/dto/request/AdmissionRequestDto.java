package com.mac.arbitrator.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdmissionRequestDto {

    private String defaultClause;

    private String jurdisction;

    private String arbitrationClause;

    private String refiefsought;

    private Float claimAmount;

    private List<ClaimantRequestDto> claimants;

    private List<RespondantRequestDto> respondants;

    private DocumentsRequestDto documents;

    private String status;

}
