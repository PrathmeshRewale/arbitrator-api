package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.FormStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateMediationFormStatus {
    private Long mediationFormId;
    private FormStatus status;
}
