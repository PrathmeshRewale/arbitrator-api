package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PartyTypeResponseDto {
    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;
}
