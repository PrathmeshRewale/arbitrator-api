package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.CaseStatus;
import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LegalCaseResponseDto {
    private Long id;
    private String caseNo;
    private Long arbitratorId;
    private String arbitratorName;
    private CaseStatus caseStatus;
    private Instant createdAt;
    private LegalCaseHearingScheduleResponseDto legalCaseHearingScheduleResponseDto;
    private List<LegalCaseLogResponseDto> legalCaseLogResponseDtos;
    private List<LegalCaseClaimantDocumentResponseDto> legalCaseClaimantDocumentResponseDtos;
    private List<LegalCaseRespondantDocumentResponseDto> legalCaseRespondantDocumentResponseDtos;
}
