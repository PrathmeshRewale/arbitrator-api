package com.mac.arbitrator.dto.request.update;

public record UpdateCaseDocumentRequest(
        String section17DocPath,
        String statementOfClaimPath,
        Long updatedById,
        String updatedByName
) {
}
