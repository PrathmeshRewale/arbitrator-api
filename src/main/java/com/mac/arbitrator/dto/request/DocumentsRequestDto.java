package com.mac.arbitrator.dto.request;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentsRequestDto {

    private Long admissionFormId;

    private String poaLoaIdCard;

    private String lrnDemandNotice;

    private String agreementContract;

    private String orders;

}
