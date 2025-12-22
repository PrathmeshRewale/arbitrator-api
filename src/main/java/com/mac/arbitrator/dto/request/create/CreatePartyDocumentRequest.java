package com.mac.arbitrator.dto.request.create;

public record CreatePartyDocumentRequest(
        String documentTitle,
        Long partyId,
        Long caseId,
        String documentUrl,
        String uploadedBy
) {
}
