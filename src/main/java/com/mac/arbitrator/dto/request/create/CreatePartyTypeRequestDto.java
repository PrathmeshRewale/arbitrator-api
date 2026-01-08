package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePartyTypeRequestDto {
    private String name;
    private String description;
    private Long createdById;
    private String createdByName;
}
