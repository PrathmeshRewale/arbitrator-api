package com.mac.arbitrator.schedular;

import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;
import com.mac.arbitrator.entity.Payment;
import com.mac.arbitrator.entity.PaymentUser;
import com.mac.arbitrator.entity.Setting;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.util.MailTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class EmailSchedular {

    private final PaymentRepository paymentRepository;
    private final SettingRepository settingRepository;
    private final PaymentUserRepository paymentUserRepository;
    private final EmailService emailService;

    public EmailSchedular(PaymentRepository paymentRepository, SettingRepository settingRepository, PaymentUserRepository paymentUserRepository, EmailService emailService) {
        this.paymentRepository = paymentRepository;
        this.settingRepository = settingRepository;
        this.paymentUserRepository = paymentUserRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 * * * ?") // every hour at minute 0
    public void sendReminderPaymentMail() {

        Setting setting = settingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Scheduler settings not found"));

        Map<String, Object> emailScheduler = setting.getSchedularSetting();

        boolean enabled = (Boolean) emailScheduler.getOrDefault("enabled", false);
        int frequencyDays = ((Number) emailScheduler.getOrDefault("frequencyDays", 0)).intValue();
        String emailTimeStr = (String) emailScheduler.getOrDefault("timeOfDay", "09:00");

        if (!enabled || frequencyDays <= 0) return;

        LocalTime configuredTime = LocalTime.parse(emailTimeStr);
        LocalTime now = LocalTime.now();

        // run only when hour matches
        if (now.getHour() != configuredTime.getHour()) return;

        LocalDate today = LocalDate.now();

        List<Payment> pendingPayments = paymentRepository.findByRemainingAmountGreaterThan(0f);

        for (Payment payment : pendingPayments) {
            long daysDiff = ChronoUnit.DAYS.between(payment.getCreatedAt(), today);

            if (daysDiff >= frequencyDays) {

                if (payment.getAdmissionId() != null) {
                    sendAdmissionReminder(payment);
                }

                if (payment.getMediationId() != null) {
                    sendMediationReminder(payment);
                }
            }
        }
    }


    private void sendAdmissionReminder(Payment payment) {
        // fetch claimant + respondent emails using repositories
        List<PaymentUser> paymentUsers = paymentUserRepository.findByPaymentId(payment.getId());

        for(PaymentUser paymentUser1 : paymentUsers){
            // build mail content
            String messageBody = MailTemplate.generateAdmissionPaymentReminderEmail(payment.getAdmissionId(),paymentUser1.getUserEmail(),paymentUser1.getUserCaseType(),paymentUser1.getAmount());

            // call emailService.send(...)
            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(paymentUser1.getUserEmail());

            emailService.sendSystemMail(createEmailRequestDto);
        }
    }

    private void sendMediationReminder(Payment payment) {
        // fetch claimant + respondent emails using repositories
        List<PaymentUser> paymentUsers = paymentUserRepository.findByPaymentId(payment.getId());

        for(PaymentUser paymentUser1 : paymentUsers){
            // build mail content
            String messageBody = MailTemplate.generateMediationPaymentReminderEmail(payment.getMediationId(),paymentUser1.getUserEmail(),paymentUser1.getUserCaseType(),paymentUser1.getAmount());

            // call emailService.send(...)
            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(paymentUser1.getUserEmail());

            emailService.sendSystemMail(createEmailRequestDto);
        }
    }

}
