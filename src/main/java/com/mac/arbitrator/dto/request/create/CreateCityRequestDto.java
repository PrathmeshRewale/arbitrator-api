package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCityRequestDto {
    private String name;
    private Long stateId;
    private String stateName;
    private Long createdById;
    private String createdByName;
}
