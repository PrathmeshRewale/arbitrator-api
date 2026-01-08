package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequestDto {
    private String description;
    private Map<String ,Object> permissions;
    private Long updatedById;
    private String updatedByName;
    private Instant updatedAt;
}
