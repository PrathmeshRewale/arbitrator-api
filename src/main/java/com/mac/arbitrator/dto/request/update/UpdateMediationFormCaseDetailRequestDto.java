package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateMediationFormCaseDetailRequestDto {
    private Long mediationFormId;
    private String recordingLink;
}
