package com.mac.arbitrator.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDto {
    List<PaymentAdmissionFormResponseDto> paymentAdmissionFormResponseDtos;
    List<PaymentMediationFormResponseDto> paymentMediationFormResponseDtos;
}
