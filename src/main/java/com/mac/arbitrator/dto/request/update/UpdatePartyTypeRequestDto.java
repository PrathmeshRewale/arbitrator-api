package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePartyTypeRequestDto {
    private String name;
    private String description;
    private Long updatedById;
    private String updatedByName;
}
