package com.mac.arbitrator.dto.request.update;

import java.time.LocalDate;

public record UpdateCaseHearingScheduleRequest(
        Long caseId,
        String status,
        LocalDate lastHearing,
        LocalDate nextHearing,
        Long updatedById,
        String updatedByName
) {
}
