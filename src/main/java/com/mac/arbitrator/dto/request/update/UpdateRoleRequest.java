package com.mac.arbitrator.dto.request.update;

import java.util.Map;

public record UpdateRoleRequest(
        String name,
        String description,
        Map<String, Object> permissions,
        Long updatedById,
        String updatedByName
) {
}
