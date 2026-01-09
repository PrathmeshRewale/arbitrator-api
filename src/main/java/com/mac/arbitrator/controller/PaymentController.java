package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateMediationFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserTypeRequestDto;
import com.mac.arbitrator.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/payment2")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    public String gettest(){
        return  "sahil";
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
        GenericResponseDto response = paymentService.updateUserPayment(requestDto);
        return ResponseEntity.ok(response);
    }
}
