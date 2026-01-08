package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCityRequestDto {
    private String name;
    private Long stateId;
    private String stateName;
    private Long updatedById;
    private String updatedByName;
}
