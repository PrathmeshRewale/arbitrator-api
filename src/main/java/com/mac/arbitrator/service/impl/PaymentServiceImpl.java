package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;
import com.mac.arbitrator.dto.request.create.CreateMediationFormUserPaymentRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePaymentUserTypeRequestDto;
import com.mac.arbitrator.entity.Payment;
import com.mac.arbitrator.entity.PaymentUser;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.AdmissionFormRepository;
import com.mac.arbitrator.repository.MediationFormRepository;
import com.mac.arbitrator.repository.PaymentRepository;
import com.mac.arbitrator.repository.PaymentUserRepository;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.PaymentService;
import com.mac.arbitrator.util.BackGroundTaskService;
import com.mac.arbitrator.util.MailTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final MediationFormRepository mediationFormRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentUserRepository paymentUserRepository;
    private final BackGroundTaskService backGroundTaskService;
    private final EmailService emailService;

    public PaymentServiceImpl(MediationFormRepository mediationFormRepository, AdmissionFormRepository admissionFormRepository, PaymentRepository paymentRepository, PaymentUserRepository paymentUserRepository, BackGroundTaskService backGroundTaskService, EmailService emailService) {
        this.mediationFormRepository = mediationFormRepository;
        this.admissionFormRepository = admissionFormRepository;
        this.paymentRepository = paymentRepository;
        this.paymentUserRepository = paymentUserRepository;
        this.backGroundTaskService = backGroundTaskService;
        this.emailService = emailService;
    }

    @Override
    public GenericResponseDto createAdmissionFormUserPayments(CreateAdmissionFormUserPaymentRequestDto req) {
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
            String messageBody = MailTemplate.generateAdmissionFormPaymentEmail(req.getAdmissionFormId(),obj.getUserEmail(),obj.getType(),obj.getAmount());
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
        Payment payment = new Payment();
        payment.setMediationId(req.getMediationFormId());
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
            String messageBody = MailTemplate.generateMediationFormPaymentEmail(req.getMediationFormId(),obj.getUserEmail(),obj.getType(),obj.getAmount());
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

        PaymentUser paymentUser = new PaymentUser();
        paymentUser.setPaymentId(req.getPaymentId());
        paymentUser.setPaymentMode(req.getPaymentMode());
        paymentUser.setPaidById(req.getPaidById());
        paymentUser.setPaymentStatus(PaymentStatus.PAID);
        paymentUser.setTransactionId(req.getTransactionId());
        paymentUser.setPaidByName(req.getPaidByName());

        PaymentUser user = paymentUserRepository.save(paymentUser);

        Payment payment = paymentRepository.findById(req.getPaymentId()).orElseThrow(()->new RuntimeException("Payment with id -> " + req.getPaymentId() + " not found"));

        Float newRemainingAmount = payment.getRemainingAmount() - req.getAmount();

        payment.setRemainingAmount(newRemainingAmount);

        Payment payment1 = paymentRepository.save(payment);

        if(payment1.getRemainingAmount() == 0){
            backGroundTaskService.createCaseAndAssignArbitrators(payment1.getAdmissionId());
        }

        return null;
    }
}
