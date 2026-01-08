package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMediationDocumentsRequestDto {
    private String poaLoaIdCard;

    private String lrnDemandNotice;

    private String agreementContract;
}
