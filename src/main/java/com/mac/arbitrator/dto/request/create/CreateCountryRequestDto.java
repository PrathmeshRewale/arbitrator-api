package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCountryRequestDto {
    private String name;
    private Long createdById;
    private String createdByName;
}
