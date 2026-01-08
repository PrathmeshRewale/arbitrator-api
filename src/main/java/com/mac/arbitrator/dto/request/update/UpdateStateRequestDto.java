package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStateRequestDto {
    private String name;
    private Long countryId;
    private String countryName;
    private Long updatedById;
    private String updatedByName;
}
