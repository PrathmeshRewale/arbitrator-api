package com.mac.arbitrator.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentAdmissionFormResponseDto {
    private String admissionFormNo;
    private Float totalAmount;
    private Float remainingAmount;
    List<PaymentUserResponseDto> paymentUserResponseDtos;
}
