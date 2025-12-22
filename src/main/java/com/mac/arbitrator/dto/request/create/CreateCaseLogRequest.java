package com.mac.arbitrator.dto.request.create;

import java.time.Instant;
import java.time.LocalDate;

public record CreateCaseLogRequest(
        String arbitrator,
        LocalDate lastHearingDate,
        LocalDate nextHearingDate,
        String purposeOfHearing,
        String attachment,
        Long caseId,
        Long createdById,
        String createdByName
) {
}
