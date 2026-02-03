package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.service.PaymentService;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${razorpay.key_secret}")  // ✅ correct
    private String keySecret;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/admission_form")
    public ResponseEntity<GenericResponseDto> createAdmissionFormUserPayment(
            @RequestBody CreateAdmissionFormUserPaymentRequestDto requestDto) {
        return ResponseEntity.ok(paymentService.createAdmissionFormUserPayments(requestDto));
    }

    @PostMapping("/mediation_form")
    public ResponseEntity<GenericResponseDto> createMediationFormUserPayment(
            @RequestBody CreateMediationFormUserPaymentRequestDto requestDto) {
        GenericResponseDto response = paymentService.createMediationFormUserPayment(requestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponseDto> updateUserPayment(
            @RequestBody CreatePaymentUserTypeRequestDto requestDto) {
        GenericResponseDto response = paymentService.updateUserPayment(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPayment(){
        return ResponseEntity.ok(paymentService.getAllPayment());
    }

    @GetMapping("/admission/{paymentId}")
    public ResponseEntity<?> getAdmissionFormPaymentByPaymentId(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getAllAdmissionFormPaymentByPaymentId(paymentId));
    }

    @GetMapping("/mediation/{paymentId}")
    public ResponseEntity<?> getMediationFormPaymentByPaymentId(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getAllMediationFormPaymentByPaymentId(paymentId));
    }

    @PostMapping("rayzorpay")
    public ResponseEntity<Map<String, Object>> getPaymentLink(@RequestBody CreatePaymentUserRequestDto createPaymentRequest){
        return ResponseEntity.ok(paymentService.createRayzorpayPayment(createPaymentRequest));
    }

    @PostMapping("/rayzorpay/payment-callback")
    public ResponseEntity<?> paymentCallback(
            @RequestBody CreateRazorpayCallbackRequest request
    ) throws RazorpayException {

        String payload = request.razorpay_order_id() + "|" + request.razorpay_payment_id();
        boolean isValid = Utils.verifySignature(
                payload,
                request.razorpay_signature(),
                keySecret
        );

        if (isValid) {
            CreatePaymentUserTypeRequestDto updatePymentRequest = new CreatePaymentUserTypeRequestDto();
            updatePymentRequest.setPaymentId(request.paymentId());
            updatePymentRequest.setAmount(request.amount());
            updatePymentRequest.setUserEmail(request.userEmail());
            updatePymentRequest.setTransactionId(request.transactionId());
            updatePymentRequest.setPaidByUserEmail(request.paidByUserEmail());

            return ResponseEntity.ok(paymentService.updateUserRayzorpayPayment(updatePymentRequest));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "failed"
            ));
        }
    }
}
