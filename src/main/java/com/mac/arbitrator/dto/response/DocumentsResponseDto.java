package com.mac.arbitrator.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentsResponseDto {

    private Long id;

    private String poaLoaIdCard;

    private String lrnDemandNotice;

    private String agreementContract;

    private String orders;
}
