package com.mac.arbitrator.dto.request.create;

import java.time.LocalDate;

public record CreateCaseHearingScheduleRequest(
        Long caseId,
        String status,
        LocalDate lastHearing,
        LocalDate nextHearing
) {
}
