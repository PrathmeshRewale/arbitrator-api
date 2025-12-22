package com.mac.arbitrator.dto.request.update;

public record UpdateCaseLogRequest(
        String judge,
        String businessOnDate,
        String hearingDate,
        String purposeOfHearing,
        String attachment,
        Long caseId,
        Long updatedById,
        String updatedByName
) {
}
