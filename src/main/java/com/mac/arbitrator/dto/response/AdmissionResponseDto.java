package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.dto.request.DocumentsRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdmissionResponseDto {

    private Long id;

    private String defaultClause;

    private String jurdisction;

    private String arbitrationClause;

    private String refiefSought;

    private Float claimAmount;

    private List<ClaimantResponseDto> claimants;

    private List<RespondantResponseDto> respondants;

    private DocumentsResponseDto documents;

    private String status;
}
