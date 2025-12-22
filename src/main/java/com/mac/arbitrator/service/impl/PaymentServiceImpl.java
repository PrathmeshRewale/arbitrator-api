package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.AdmissionPaymentRequest;
import com.mac.arbitrator.dto.request.EmailRequest;
import com.mac.arbitrator.dto.request.create.CreatePaymentRequest;
import com.mac.arbitrator.dto.request.update.UpdatePymentRequest;
import com.mac.arbitrator.dto.response.PaymentResponseDto;
import com.mac.arbitrator.dto.response.PendingPaymentResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.ClaimantRepository;
import com.mac.arbitrator.repository.PaymentRepository;
import com.mac.arbitrator.repository.UserPaymentRepository;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserPaymentRepository userPaymentRepository;
    private final EmailService emailService;

    @Value("${rayzorpay.key_id}")
    private String KEY_ID;
    @Value("${rayzorpay.key_secret}")
    private String KEY_SECRET; // Replace with your Key Secret

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponseDto> paymentResponseDtos = new ArrayList<>();
        payments.forEach(obj->{

            PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
            paymentResponseDto.setId(obj.getId());
            paymentResponseDto.setAmount(obj.getAmount());
            paymentResponseDto.setAdmissionId(obj.getAdmissionId());
            paymentResponseDto.setRemainingAmount(obj.getRemainingAmount());
            paymentResponseDto.setCaseId(obj.getCaseId());

            List<UserPayment> userPayments = userPaymentRepository.findByPaymentId(obj.getId());

            List<PaymentResponseDto.PaymentUserResponse> paymentUserResponses = userPayments.stream().map(obj1->{
                PaymentResponseDto.PaymentUserResponse response =  paymentResponseDto.new PaymentUserResponse();
                response.setAmount(obj1.getAmount());
                response.setPaymentMode(obj1.getPaymentMode());
                response.setUserEmail(obj1.getUserEmail());
                response.setType(obj1.getUserCaseType());
                response.setStatus(obj1.getPaymentStatus());
                response.setTransactionId(obj1.getTransactionId());
                response.setPaidByName(obj1.getPaidByName());
                return response;
            }).toList();


            paymentResponseDto.setPaymentUserResponses(paymentUserResponses);
            paymentResponseDtos.add(paymentResponseDto);
        });

        return paymentResponseDtos;
    }

    @Override
    public GenericResponseDto assignUserPayment(AdmissionPaymentRequest admissionPaymentRequest) {
        try {

            // Check if payment entries already exist for the given admission
            boolean paymentsExist = paymentRepository.existsByAdmissionId(admissionPaymentRequest.getAdmissionId());
            if (paymentsExist) {
                return new GenericResponseDto("error", "Payments are already assigned for this admission");
            }

            //first saved the payment into payment repository

            Payment payment = new Payment();
            payment.setAmount(admissionPaymentRequest.getTotalAmount());
            payment.setAdmissionId(admissionPaymentRequest.getAdmissionId());
            payment.setCreatedAt(Instant.now());
            payment.setRemainingAmount(admissionPaymentRequest.getTotalAmount());

            // Save all payments
            Payment payment1 = paymentRepository.saveAndFlush(payment);

            //now only if the payment is created then we will assign the payments

            List<UserPayment> userPayments = admissionPaymentRequest.getUserAdmountDetails().stream().map(
                    obj-> {
                        return UserPayment.builder()
                                .paymentId(payment1.getId())
                                .paymentStatus(PaymentStatus.UNPAID)
                                .amount(obj.getAmount())
                                .transactionId(null)
                                .userCaseType(obj.getType())
                                .userEmail(obj.getUserEmail())
                                .paidById(null)
                                .paidByName(null)
                                .build();
                    }
            ).toList();

            // Save all userpayments
            userPaymentRepository.saveAllAndFlush(userPayments);

            //now send the email about the case register and they can register their account can see the amount
            admissionPaymentRequest.getUserAdmountDetails().forEach(obj->sendCaseEmail(obj.getUserEmail(),obj.getType(),admissionPaymentRequest.getAdmissionId()));
            return new GenericResponseDto("success", "Payments assigned successfully");

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<PendingPaymentResponseDto> getPendingPaymentStatusByCaseId(Long caseId) {
        Payment payment = paymentRepository.findByCaseId(caseId);
        List<UserPayment> userPayments = userPaymentRepository.findByPaymentId(payment.getId());
        List<PendingPaymentResponseDto> paymentResponseDtos = new ArrayList<>();
        userPayments.forEach(obj->{
            PendingPaymentResponseDto pendingPaymentResponseDto = new PendingPaymentResponseDto();
            pendingPaymentResponseDto.setPaymentId(payment.getId());
            pendingPaymentResponseDto.setPaymentStatus(obj.getPaymentStatus());
            pendingPaymentResponseDto.setAmount(obj.getAmount());
            pendingPaymentResponseDto.setUserCaseType(obj.getUserCaseType());
            pendingPaymentResponseDto.setUserEmail(obj.getUserEmail());
            paymentResponseDtos.add(pendingPaymentResponseDto);
        });

        return paymentResponseDtos;
    }

    @Override
    public GenericResponseDto updatePayment(Long id, UpdatePymentRequest updatePymentRequest) {
        UserPayment userPayment = userPaymentRepository.findByPaymentIdAndUserEmail(id,updatePymentRequest.userEmail());
        userPayment.setPaymentMode(updatePymentRequest.paymentMode());
        userPayment.setPaymentStatus(updatePymentRequest.status());
        userPayment.setTransactionId(updatePymentRequest.transactionId());
        userPayment.setPaidById(updatePymentRequest.paidById());
        userPayment.setPaidByName(updatePymentRequest.paidByName());

        userPaymentRepository.save(userPayment);

        Payment payment = paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment with id -> " + id + " not found"));
        payment.setRemainingAmount((payment.getRemainingAmount()-userPayment.getAmount()));

        paymentRepository.save(payment);
        return new GenericResponseDto("success", "Payments updated successfully");
    }


    @Override
    public Map<String, Object> createRayzorpayPayment(CreatePaymentRequest request) {
        try {
            RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", request.amount() * 100); // paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "rcpt_" + System.currentTimeMillis());

            JSONObject notes = new JSONObject();
            notes.put("user_email", request.userName());
            orderRequest.put("notes", notes);

            Order order = razorpay.orders.create(orderRequest);

            // ✅ RETURN AS MAP (frontend-friendly)
            Map<String, Object> response = new HashMap<>();
            response.put("id", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }


    private void sendCaseEmail(String userEmail, UserCaseType userCaseType, Long admissionId) {

        String emailMessage = null;
        switch (userCaseType) {
            case RESPONDANT -> {
                emailMessage = EmailServiceImpl.generateRespondentEmail(
                        userEmail, admissionId);
                EmailRequest emailRequest = EmailRequest.builder()
                        .recipient(userEmail)
                        .msgBody(emailMessage)
                        .build();
                emailService.sendRespondentMail(emailRequest);
            }
            case CLAIMANT -> {
                emailMessage = EmailServiceImpl.generateClaimantEmail(
                        userEmail, admissionId);
                EmailRequest emailRequest = EmailRequest.builder()
                        .recipient(userEmail)
                        .msgBody(emailMessage)
                        .build();
                emailService.sendClaimantMail(emailRequest);
            }
            default -> {
                return;
            }
        }
    }




}
