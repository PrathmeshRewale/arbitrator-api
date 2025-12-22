package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseResponseDto {
    private Long id;
    private String caseNo;
    private String section17DocPath;
    private String statementOfClaimPath;
    private AdmissionFormStatus caseStatus;
    private List<String> respondants;
    private List<String> claimants;
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;
}
