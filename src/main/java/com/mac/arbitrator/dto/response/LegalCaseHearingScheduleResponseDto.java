package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LegalCaseHearingScheduleResponseDto {
    private Long id;
    private Long legalCaseId;
    private LocalDate lastHearingDate;
    private LocalDate nextHearingDate;
    private HearingStatus status;
}
