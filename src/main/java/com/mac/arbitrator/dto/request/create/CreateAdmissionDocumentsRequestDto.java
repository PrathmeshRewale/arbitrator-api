package com.mac.arbitrator.dto.request.create;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdmissionDocumentsRequestDto {
    private String poaLoaIdCard;

    private String lrnDemandNotice;

    private String agreementContract;

    private String orders;

}
