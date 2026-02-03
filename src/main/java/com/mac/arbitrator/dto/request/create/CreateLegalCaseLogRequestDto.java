package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLegalCaseLogRequestDto {
    private Long arbitratorId;
    private String arbitratorName;
    private Long legalCaseId;
    private LocalDate lastHearingDate;
    private LocalDate nextHearingDate;
    private String purposeOfHearing;
    private String recordingLink;
    private HearingStatus status;
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
}
