package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateStateRequestDto {
    private String name;
    private Long countryId;
    private String countryName;
    private Long createdById;
    private String createdByName;
}
