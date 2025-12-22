package com.mac.arbitrator.dto.request.update;

public record UpdateJurisdictionRequest(
        String name,
        String description,
        Long updatedById,
        String updatedByName
) {
}
