package com.mac.arbitrator.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleResponseDto {
    private Long id;
    private String name;
    private String description;
    private Map<String, Object> permissions;
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;
}
