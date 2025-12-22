package com.mac.arbitrator.dto.request.create;

public record CreateAdvocatePartyRequest(
        Long caseId,
        Long advocateId
) {
}
