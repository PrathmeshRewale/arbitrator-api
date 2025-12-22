package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreatePaymentRequest;
import com.mac.arbitrator.dto.request.create.CreateRazorpayCallbackRequest;
import com.mac.arbitrator.dto.request.update.UpdatePymentRequest;
import com.mac.arbitrator.service.PaymentService;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/apiv1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${rayzorpay.key_secret}")
    private String KEY_SECRET;

    @GetMapping("/{caseId}")
    public ResponseEntity<?> getPendingPaymentByCaseId(@PathVariable Long caseId){
        return ResponseEntity.ok(paymentService.getPendingPaymentStatusByCaseId(caseId));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllPayments(){
        return  ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Long id, @RequestBody UpdatePymentRequest updatePymentRequest){
        return ResponseEntity.ok(paymentService.updatePayment(id,updatePymentRequest));
    }

    @PostMapping("rayzorpay")
    public ResponseEntity<Map<String, Object>> getPaymentLink(@RequestBody CreatePaymentRequest createPaymentRequest){
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
                KEY_SECRET
        );

        if (isValid) {
            UpdatePymentRequest updatePymentRequest = new UpdatePymentRequest(
                    request.userEmail(),
                    request.status(),
                    request.paymentMode(),
                    request.paidByName(),
                    request.paidById(),
                    request.transactionId()
            );
            return ResponseEntity.ok(paymentService.updatePayment(request.paymentId(),updatePymentRequest));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "failed"
            ));
        }
    }
}
