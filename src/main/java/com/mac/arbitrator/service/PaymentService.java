package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.AdmissionPaymentRequest;
import com.mac.arbitrator.dto.request.create.CreatePaymentRequest;
import com.mac.arbitrator.dto.request.update.UpdatePymentRequest;
import com.mac.arbitrator.dto.response.PaymentResponseDto;
import com.mac.arbitrator.dto.response.PendingPaymentResponseDto;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    List<PaymentResponseDto> getAllPayments();
    GenericResponseDto assignUserPayment(AdmissionPaymentRequest admissionPaymentRequest);
    List<PendingPaymentResponseDto> getPendingPaymentStatusByCaseId(Long caseId);
    GenericResponseDto updatePayment(Long id, UpdatePymentRequest updatePymentRequest);
    Map<String, Object> createRayzorpayPayment(CreatePaymentRequest createPaymentRequest);
}
