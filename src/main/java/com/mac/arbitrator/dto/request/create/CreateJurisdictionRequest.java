package com.mac.arbitrator.dto.request.create;

public record CreateJurisdictionRequest(
        String name,
        String description,
        Long createdById,
        String createdByName
) {
}
