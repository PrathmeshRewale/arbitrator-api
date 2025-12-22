package com.mac.arbitrator.dto.request.create;

import java.util.Map;

public record CreateRoleRequest(
        String name,
        String description,
        Map<String, Object>permissions,
        Long createdById,
        String createdByName
) {
}
