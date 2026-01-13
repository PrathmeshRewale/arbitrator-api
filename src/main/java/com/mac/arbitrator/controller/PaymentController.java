package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateMediationFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserTypeRequestDto;
import com.mac.arbitrator.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/admission_form")
    public ResponseEntity<GenericResponseDto> createAdmissionFormUserPayment(
            @RequestBody CreateAdmissionFormUserPaymentRequestDto requestDto) {
        System.out.println("sahil");
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
        System.out.println("sahil");
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
}
