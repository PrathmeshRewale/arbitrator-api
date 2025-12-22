package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseMiniResponseDto {
    private Long caseId;
    private String caseNo;
    private AdmissionFormStatus caseStatus;
    private Instant createdAt;
    private String createdByName;
}
