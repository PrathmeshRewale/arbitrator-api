package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.dto.response.PaymentAdmissionFormResponseDto;
import com.mac.arbitrator.dto.response.PaymentMediationFormResponseDto;
import com.mac.arbitrator.dto.response.PaymentResponseDto;
import com.mac.arbitrator.dto.response.PaymentUserResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.FormStatus;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.PaymentService;
import com.mac.arbitrator.util.BackGroundTaskService;
import com.mac.arbitrator.util.MailTemplate;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key_id}")   // ✅ correct
    private String keyId;

    @Value("${razorpay.key_secret}")  // ✅ correct
    private String keySecret;


    private final MediationFormRepository mediationFormRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentUserRepository paymentUserRepository;
    private final BackGroundTaskService backGroundTaskService;
    private final EmailService emailService;
    private final LegalCaseRepository legalCaseRepository;
    private final AdmissionFormClaimantRepository admissionFormClaimantRepository;
    private final MediationFormClaimantRepository mediationFormClaimantRepository;

    public PaymentServiceImpl(MediationFormRepository mediationFormRepository, AdmissionFormRepository admissionFormRepository, PaymentRepository paymentRepository, PaymentUserRepository paymentUserRepository, BackGroundTaskService backGroundTaskService, EmailService emailService, LegalCaseRepository legalCaseRepository, AdmissionFormClaimantRepository admissionFormClaimantRepository, MediationFormClaimantRepository mediationFormClaimantRepository) {
        this.mediationFormRepository = mediationFormRepository;
        this.admissionFormRepository = admissionFormRepository;
        this.paymentRepository = paymentRepository;
        this.paymentUserRepository = paymentUserRepository;
        this.backGroundTaskService = backGroundTaskService;
        this.emailService = emailService;
        this.legalCaseRepository = legalCaseRepository;
        this.admissionFormClaimantRepository = admissionFormClaimantRepository;
        this.mediationFormClaimantRepository = mediationFormClaimantRepository;
    }

    @Override
    public GenericResponseDto createAdmissionFormUserPayments(CreateAdmissionFormUserPaymentRequestDto req) {

        AdmissionForm admissionForm = admissionFormRepository.findById(req.getAdmissionFormId()).orElseThrow(()->new RuntimeException("admission with id -> "+req.getAdmissionFormId()+" not found"));
        admissionForm.setStatus(req.getStatus());
        admissionFormRepository.save(admissionForm);

        if(req.getStatus().equals(FormStatus.REJECTED)){
            List<AdmissionFormClaimant> claimants =
                    admissionFormClaimantRepository.findByAdmissionFormId(admissionForm.getId());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            String disputeDate = admissionForm.getDisputeDate() != null
                    ? admissionForm.getDisputeDate().format(formatter)
                    : "-";

            String requestDate = admissionForm.getCreatedAt() != null
                    ? admissionForm.getCreatedAt().format(formatter)
                    : "-";

            String disputeAmount = admissionForm.getDisputeAmount() != null
                    ? NumberFormat.getCurrencyInstance(new Locale("en", "IN"))
                    .format(admissionForm.getDisputeAmount())
                    : "-";

            String message = MailTemplate.generateArbitrationRejectedEmail(
                    admissionForm.getJurisdictionName(),
                    admissionForm.getDefaultClause(),
                    admissionForm.getArbitrationClause(),
                    admissionForm.getReliefSought(),
                    disputeDate,          // formatted
                    disputeAmount,        // formatted
                    requestDate,          // formatted
                    req.getRejectReason()
            );

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();

            claimants.forEach(obj->{
                createEmailRequestDto.setRecipient(obj.getEmail());
                createEmailRequestDto.setMsgBody(message);
                emailService.sendSystemMail(createEmailRequestDto);
            });

            return new GenericResponseDto("success","Admission form rejects");
        }

        Payment payment = new Payment();
        payment.setAdmissionId(req.getAdmissionFormId());
        payment.setCreatedAt(LocalDate.now());
        payment.setAmount(req.getAmount());
        payment.setRemainingAmount(req.getRemainingAmount());

        Payment payment1 = paymentRepository.save(payment);

        req.getAdmissionFormUserAmountDetails().forEach(obj->{
            PaymentUser paymentUser = new PaymentUser();
            paymentUser.setPaymentId(payment1.getId());
            paymentUser.setPaymentStatus(PaymentStatus.UNPAID);
            paymentUser.setUserEmail(obj.getUserEmail());
            paymentUser.setUserCaseType(obj.getType());
            paymentUser.setAmount(obj.getAmount());

            paymentUserRepository.save(paymentUser);

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            String messageBody = MailTemplate.generateAdmissionFormPaymentEmail(payment1.getId(),obj.getUserEmail(),obj.getType(),obj.getAmount());
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(obj.getUserEmail());
            if(obj.getType().equals(UserCaseType.CLAIMANT)){
                emailService.sendClaimantMail(createEmailRequestDto);
            }else{
                emailService.sendRespondentMail(createEmailRequestDto);
            }
        });
        return new GenericResponseDto("success","Payment Assigned to all users successfully");
    }

    @Override
    public GenericResponseDto createMediationFormUserPayment(CreateMediationFormUserPaymentRequestDto req) {

        MediationForm mediationForm = mediationFormRepository.findById(req.getMediationFormId()).orElseThrow(()->new RuntimeException("mediation with id -> "+req.getMediationFormId()+" not found"));
        mediationForm.setStatus(req.getStatus());
        mediationFormRepository.save(mediationForm);

        if(req.getStatus().equals(FormStatus.REJECTED)){
            List<MediationFormClaimant> claimants =
                    mediationFormClaimantRepository.findByMediationFormId(mediationForm.getId());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            String disputeDate = mediationForm.getDisputeDate() != null
                    ? mediationForm.getDisputeDate().format(formatter)
                    : "-";

            String requestDate = mediationForm.getCreatedAt() != null
                    ? mediationForm.getCreatedAt().format(formatter)
                    : "-";

            String disputeAmount = mediationForm.getDisputeAmount() != null
                    ? NumberFormat.getCurrencyInstance(new Locale("en", "IN"))
                    .format(mediationForm.getDisputeAmount())
                    : "-";

            String message = MailTemplate.generateArbitrationRejectedEmail(
                    mediationForm.getJurisdictionName(),
                    mediationForm.getDefaultClause(),
                    mediationForm.getArbitrationClause(),
                    mediationForm.getReliefSought(),
                    disputeDate,          // formatted
                    disputeAmount,        // formatted
                    requestDate,          // formatted
                    req.getRejectReason()
            );

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();

            claimants.forEach(obj->{
                createEmailRequestDto.setRecipient(obj.getEmail());
                createEmailRequestDto.setMsgBody(message);
                emailService.sendSystemMail(createEmailRequestDto);
            });

            return new GenericResponseDto("success","Mediation form rejects");
        }

        Payment payment = new Payment();
        payment.setMediationId(req.getMediationFormId());
        payment.setCreatedAt(LocalDate.now());
        payment.setAmount(req.getAmount());
        payment.setRemainingAmount(req.getRemainingAmount());

        Payment payment1 = paymentRepository.save(payment);

        req.getMediationFormUserAmountDetails().forEach(obj->{
            PaymentUser paymentUser = new PaymentUser();
            paymentUser.setPaymentId(payment1.getId());
            paymentUser.setPaymentStatus(PaymentStatus.UNPAID);
            paymentUser.setUserEmail(obj.getUserEmail());
            paymentUser.setUserCaseType(obj.getType());
            paymentUser.setAmount(obj.getAmount());

            paymentUserRepository.save(paymentUser);

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            String messageBody = MailTemplate.generateMediationFormPaymentEmail(payment1.getId(),obj.getUserEmail(),obj.getType(),obj.getAmount());
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(obj.getUserEmail());
            if(obj.getType().equals(UserCaseType.CLAIMANT)){
                emailService.sendClaimantMail(createEmailRequestDto);
            }else{
                emailService.sendRespondentMail(createEmailRequestDto);
            }
        });
        return new GenericResponseDto("success","Payment Assigned to all users successfully");
    }

    @Override
    public GenericResponseDto updateUserPayment(CreatePaymentUserTypeRequestDto req) {

        System.out.println("===== updateUserPayment START =====");
        System.out.println("Request: " + req);

        PaymentUser paymentUser = paymentUserRepository
                .findByPaymentIdAndUserEmail(req.getPaymentId(), req.getUserEmail());

        if (paymentUser == null) {
            System.out.println("PaymentUser NOT FOUND for PaymentId: "
                    + req.getPaymentId() + " Email: " + req.getUserEmail());
            return new GenericResponseDto("error", "Payment user not found");
        }

        System.out.println("PaymentUser Found: " + paymentUser.getId());

        paymentUser.setPaymentId(req.getPaymentId());
        paymentUser.setPaymentMode(req.getPaymentMode());
        paymentUser.setPaymentStatus(PaymentStatus.PAID);
        paymentUser.setTransactionId(req.getTransactionId());
        paymentUser.setPaidByUserEmail(req.getPaidByUserEmail());

        PaymentUser savedUser = paymentUserRepository.save(paymentUser);
        System.out.println("PaymentUser Updated Successfully: " + savedUser.getId());

        Payment payment = paymentRepository.findById(req.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment with id -> " + req.getPaymentId() + " not found"));

        System.out.println("Payment Before Update Remaining Amount: " + payment.getRemainingAmount());

        Float newRemainingAmount = payment.getRemainingAmount() - req.getAmount();
        payment.setRemainingAmount(newRemainingAmount);

        Payment payment1 = paymentRepository.save(payment);
        System.out.println("Payment After Update Remaining Amount: " + payment1.getRemainingAmount());

        if (payment1.getRemainingAmount() == 0) {
            System.out.println("Remaining amount is ZERO. Creating case & assigning arbitrators...");
            if(payment1.getAdmissionId() != null) {
                backGroundTaskService.createCaseAndAssignArbitratorForAdmissionForm(payment1.getAdmissionId());
                backGroundTaskService.sendClaimantAndRespondantEmailForAdmissionForm(payment1.getAdmissionId());
            }else{
                backGroundTaskService.AssignArbitratorForMediationForm(payment1.getMediationId());
            }
        } else {
            System.out.println("Remaining amount still pending: " + payment1.getRemainingAmount());
        }

        System.out.println("===== updateUserPayment END =====");

        return new GenericResponseDto("success", "Payment updated successfully for " + req.getUserEmail());
    }

    @Override
    public GenericResponseDto updateUserRayzorpayPayment(CreatePaymentUserTypeRequestDto updatePymentRequest) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(
                keyId,
                keySecret
        );

        com.razorpay.Payment payment = razorpayClient.payments.fetch(
                updatePymentRequest.getTransactionId()
        );

        String paymentMethod = payment.get("method");

        updatePymentRequest.setPaymentMode(paymentMethod);

        return updateUserPayment(updatePymentRequest);
    }

    @Override
    public PaymentResponseDto getAllPayment() {

        List<Payment> payments = paymentRepository.findAll();

        List<PaymentAdmissionFormResponseDto> admissionList = new ArrayList<>();
        List<PaymentMediationFormResponseDto> mediationList = new ArrayList<>();

        for (Payment payment : payments) {

            List<PaymentUser> users = paymentUserRepository.findByPaymentId(payment.getId());

            List<PaymentUserResponseDto> userDtos = users.stream().map(u ->
                    new PaymentUserResponseDto(
                            u.getPaymentId(),
                            u.getPaidByUserEmail(),
                            u.getUserEmail(),
                            u.getUserCaseType(),
                            u.getAmount(),
                            u.getTransactionId(),
                            u.getPaymentStatus(),
                            u.getPaymentMode()
                    )
            ).toList();

            if (payment.getAdmissionId() != null) {
                AdmissionForm form = admissionFormRepository.findById(payment.getAdmissionId()).orElse(null);
                LegalCase legalCase = legalCaseRepository.findByAdmissionFormId(payment.getAdmissionId()).orElse(null);

                admissionList.add(new PaymentAdmissionFormResponseDto(
                        form != null ? form.getAdmissionFormNo() : null,
                        payment.getAmount(),
                        payment.getRemainingAmount(),
                        userDtos
                ));
            }

            if (payment.getMediationId() != null) {
                MediationForm form = mediationFormRepository.findById(payment.getMediationId()).orElse(null);

                mediationList.add(new PaymentMediationFormResponseDto(
                        form != null ? form.getMediationFormNo() : null,
                        payment.getAmount(),
                        payment.getRemainingAmount(),
                        userDtos
                ));
            }
        }

        return new PaymentResponseDto(admissionList, mediationList);
    }

    @Override
    public PaymentAdmissionFormResponseDto getAllAdmissionFormPaymentByPaymentId(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        AdmissionForm form = admissionFormRepository.findById(payment.getAdmissionId())
                .orElseThrow(() -> new RuntimeException("Admission form not found"));

        LegalCase legalCase = legalCaseRepository.findByAdmissionFormId(payment.getAdmissionId()).orElse(null);

        List<PaymentUserResponseDto> users = paymentUserRepository.findByPaymentId(paymentId)
                .stream()
                .map(u -> new PaymentUserResponseDto(
                        u.getPaymentId(),
                        u.getPaidByUserEmail(),
                        u.getUserEmail(),
                        u.getUserCaseType(),
                        u.getAmount(),
                        u.getTransactionId(),
                        u.getPaymentStatus(),
                        u.getPaymentMode()
                ))
                .toList();

        return new PaymentAdmissionFormResponseDto(
                form.getAdmissionFormNo(),
                payment.getAmount(),
                payment.getRemainingAmount(),
                users
        );
    }

    @Override
    public PaymentMediationFormResponseDto getAllMediationFormPaymentByPaymentId(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        MediationForm form = mediationFormRepository.findById(payment.getMediationId())
                .orElseThrow(() -> new RuntimeException("Mediation form not found"));

        List<PaymentUserResponseDto> users = paymentUserRepository.findByPaymentId(paymentId)
                .stream()
                .map(u -> new PaymentUserResponseDto(
                        u.getPaymentId(),
                        u.getPaidByUserEmail(),
                        u.getUserEmail(),
                        u.getUserCaseType(),
                        u.getAmount(),
                        u.getTransactionId(),
                        u.getPaymentStatus(),
                        u.getPaymentMode()
                ))
                .toList();

        return new PaymentMediationFormResponseDto(
                form.getMediationFormNo(),
                payment.getAmount(),
                payment.getRemainingAmount(),
                users
        );
    }


    @Override
    public Map<String, Object> createRayzorpayPayment(CreatePaymentUserRequestDto request) {
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", request.getAmount() * 100); // paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "rcpt_" + System.currentTimeMillis());

            JSONObject notes = new JSONObject();
            notes.put("user_email", request.getUserName());
            orderRequest.put("notes", notes);

            Order order = razorpay.orders.create(orderRequest);

            // ✅ RETURN AS MAP (frontend-friendly)
            Map<String, Object> response = new HashMap<>();
            response.put("id", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));

            return response;

        } catch (Exception e) {
            e.printStackTrace(); // 👈 very important
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }
}
