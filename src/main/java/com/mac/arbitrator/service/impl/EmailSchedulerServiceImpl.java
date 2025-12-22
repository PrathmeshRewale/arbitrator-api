package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.request.EmailRequest;
import com.mac.arbitrator.entity.Payment;
import com.mac.arbitrator.entity.Setting;
import com.mac.arbitrator.entity.UserPayment;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.PaymentRepository;
import com.mac.arbitrator.repository.SettingRepository;
import com.mac.arbitrator.repository.UserPaymentRepository;
import com.mac.arbitrator.service.EmailSchedulerService;
import com.mac.arbitrator.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mac.arbitrator.entity.enums.PaymentStatus.PAID;

@Service
@RequiredArgsConstructor
public class EmailSchedulerServiceImpl implements EmailSchedulerService {

    private final SettingRepository settingRepository;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;
    private final UserPaymentRepository userPaymentRepository;


    // Stores the last run date for each scheduler key (to prevent duplicate runs in a day)
    private final Map<String, LocalDate> lastRunMap = new ConcurrentHashMap<>();

    /**
     * 📨 Main scheduler logic: handles both email reminders & fund transfers.
     */
    @Override
    public void handleEmailScheduler() {
        Map<String, Object> map = getAllSchedulerConfig();

        Map<String, Object> transferScheduler = (Map<String, Object>) map.get("transferScheduler");
        Map<String, Object> emailScheduler = (Map<String, Object>) map.get("emailScheduler");

        boolean transferEnabled = (boolean) transferScheduler.getOrDefault("enabled", false);
        boolean emailEnabled = (boolean) emailScheduler.getOrDefault("enabled", false);

        int transferDays = ((Number) transferScheduler.getOrDefault("frequencyDays", 0)).intValue();
        int emailDays = ((Number) emailScheduler.getOrDefault("frequencyDays", 0)).intValue();

        String transferTimeStr = (String) transferScheduler.getOrDefault("timeOfDay", "00:00");
        String emailTimeStr = (String) emailScheduler.getOrDefault("timeOfDay", "09:00");

        LocalTime transferTime = LocalTime.parse(transferTimeStr);
        LocalTime emailTime = LocalTime.parse(emailTimeStr);

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Payment> payments = paymentRepository.findAll();

        payments.forEach(obj->{
            for (UserPayment payment : userPaymentRepository.findByPaymentId(obj.getId())) {
                LocalDate createdAt = LocalDate.ofInstant(
                        obj.getCreatedAt(),
                        ZoneId.systemDefault()
                );


                // 🧠 Skip already transferred (PAID) payments
                if (payment.getPaymentStatus() == PAID) {
//                System.out.println("✅ Already transferred. Skipping admission: " + payment.getAdmissionId());
                    continue;
                }

                // --- EMAIL LOGIC ---
                if (emailEnabled) {
                    LocalDate emailDueDate = createdAt.plusDays(emailDays);
                    LocalDate emailStartDate = emailDueDate.minusDays(7);

                    boolean isEmailTime = Math.abs(Duration.between(now, emailTime).getSeconds()) <= 30;
                    long daysSinceStart = ChronoUnit.DAYS.between(emailStartDate, today);
                    boolean isSendDay = daysSinceStart >= 0 && daysSinceStart % emailDays == 0;

                    String emailKey = payment.getId() + "-" + today;
                    boolean shouldSend = isSendDay && isEmailTime && !lastRunMap.containsKey(emailKey);

                    // Don’t send email if transfer has already happened or same-day transfer occurred
                    if (shouldSend) {
//                    System.out.println("📧 Sending reminder email to " + payment.getUserEmail() + " at " + now);
                        lastRunMap.put(emailKey, today);
                        sendPaymentReminder(obj.getAdmissionId(),payment.getAmount(),payment.getUserEmail(),payment.getUserCaseType());
                    }
                }
            }
        });
    }

    /**
     * ✉️ Sends payment reminder email
     */
    private void sendPaymentReminder(Long admissionId,Float amount,String userEmail,UserCaseType userCaseType) {
        if (amount != 0 || userEmail == null || admissionId == null) {
//            System.err.println("⚠️ Invalid payment data, skipping email.");
            return;
        }

        try {
            String messageBody;

            switch (userCaseType) {
                case CLAIMANT -> {
                    messageBody = EmailServiceImpl.generateClaimantPaymentReminderEmail(
                            userEmail,
                            admissionId,
                            amount
                    );
                    emailService.sendClaimantMail(
                            EmailRequest.builder()
                                    .recipient(userEmail)
                                    .msgBody(messageBody)
                                    .build()
                    );
//                    System.out.println("📧 Sent claimant payment reminder to " + recipientEmail);
                }
                case RESPONDANT -> {
                    messageBody = EmailServiceImpl.generateRespondentPaymentReminderEmail(
                            userEmail,
                            admissionId,
                            amount
                    );
                    emailService.sendRespondentMail(
                            EmailRequest.builder()
                                    .recipient(userEmail)
                                    .msgBody(messageBody)
                                    .build()
                    );
//                    System.out.println("📧 Sent respondent payment reminder to " + recipientEmail);
                }
                default -> System.err.println("⚠️ Unknown user type for email: " + userCaseType);
            }
        } catch (Exception e) {
//            System.err.println("❌ Failed to send payment reminder to " + payment.getUserEmail() + ": " + e.getMessage());
//            e.printStackTrace();
        }
    }

    /**
     * 🔁 Returns fixed minimal interval (used by scheduler trigger)
     */
    @Override
    public Instant getMinimumDelayMillis() {
        return Instant.now().plusMillis(1);
    }

    /**
     * 🧩 Loads scheduler config JSON from DB
     */
    private Map<String, Object> getAllSchedulerConfig() {
        try {
            Setting setting = settingRepository.findAll().get(0);
            return objectMapper.readValue(setting.getSchedularSetting(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load scheduler config", e);
        }
    }
}
