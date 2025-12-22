package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.CaseHearingSchedule;
import com.mac.arbitrator.entity.enums.HearingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
    public class CaseHearingScheduleResponseDto {
    private LocalDate lastHearing;
    private LocalDate nextHearing;
    private HearingStatus status;
}
