package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateMediationFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserTypeRequestDto;
import com.mac.arbitrator.dto.response.PaymentAdmissionFormResponseDto;
import com.mac.arbitrator.dto.response.PaymentMediationFormResponseDto;
import com.mac.arbitrator.dto.response.PaymentResponseDto;
import com.razorpay.RazorpayException;

import java.util.Map;

public interface PaymentService {
    GenericResponseDto createAdmissionFormUserPayments(CreateAdmissionFormUserPaymentRequestDto createAdmissionFormUserPaymentRequestDto);
    GenericResponseDto createMediationFormUserPayment(CreateMediationFormUserPaymentRequestDto createMediationFormUserPaymentRequestDto);
    GenericResponseDto updateUserPayment(CreatePaymentUserTypeRequestDto createPaymentUserTypeRequestDto);
    PaymentResponseDto getAllPayment();
    PaymentAdmissionFormResponseDto getAllAdmissionFormPaymentByPaymentId(Long paymentId);
    PaymentMediationFormResponseDto getAllMediationFormPaymentByPaymentId(Long paymentId);
    Map<String, Object> createRayzorpayPayment(CreatePaymentUserRequestDto request);
    GenericResponseDto updateUserRayzorpayPayment(CreatePaymentUserTypeRequestDto updatePymentRequest) throws RazorpayException;
}
