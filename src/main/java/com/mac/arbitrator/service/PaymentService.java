package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateMediationFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserTypeRequestDto;

public interface PaymentService {
    GenericResponseDto createAdmissionFormUserPayments(CreateAdmissionFormUserPaymentRequestDto createAdmissionFormUserPaymentRequestDto);
    GenericResponseDto createMediationFormUserPayment(CreateMediationFormUserPaymentRequestDto createMediationFormUserPaymentRequestDto);
    GenericResponseDto updateUserPayment(CreatePaymentUserTypeRequestDto createPaymentUserTypeRequestDto);
}
