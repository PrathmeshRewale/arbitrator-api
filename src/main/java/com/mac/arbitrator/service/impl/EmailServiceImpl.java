package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;
import com.mac.arbitrator.entity.Setting;
import com.mac.arbitrator.repository.SettingRepository;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.util.MailUtil;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final SettingRepository settingRepository;

    public EmailServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    private String adminMail;
    private String adminSubject;
    private String claimantSubject;
    private String respondantSubject;

    @Override
    public boolean sendClaimantMail(CreateEmailRequestDto emailRequest) {
        return sendMail(emailRequest.getRecipient(), claimantSubject, emailRequest.getMsgBody());
    }

    @Override
    public boolean sendRespondentMail(CreateEmailRequestDto emailRequest) {
        return sendMail(emailRequest.getRecipient(), respondantSubject, emailRequest.getMsgBody());
    }

    @Override
    public boolean sendAdminMail(CreateEmailRequestDto emailRequest) {
        return sendMail(emailRequest.getRecipient(), adminSubject, emailRequest.getMsgBody());
    }

    @Override
    public boolean sendSystemMail(CreateEmailRequestDto createEmailRequestDto) {
        return sendMail(createEmailRequestDto.getRecipient(), "MAC Arbitration Center", createEmailRequestDto.getMsgBody());
    }

    @Override
    public String getAdminReceiverEmail() {
        Setting setting = settingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No email settings found in DB"));

        Map<String, Object> emailMap = setting.getEmailSetting();

        return (String) emailMap.getOrDefault(
                "adminReceiverMail",
                "example@gmail.com"
        );
    }


    private JavaMailSender getJavaMailSenderFromSettings() {
        Setting setting = settingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No email settings found in DB"));

        Map<String, Object> emailMap = setting.getEmailSetting();

        String host = (String) emailMap.getOrDefault("host", "localhost");
        int port = ((Number) emailMap.getOrDefault("port", 25)).intValue();
        String username = (String) emailMap.getOrDefault("username", "");
        String password = (String) emailMap.getOrDefault("password", "");

        return MailUtil.getMailSender(host, port, username, password);
    }


    @PostConstruct
    private void initEmailSettings() {
        Setting setting = settingRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No email settings found in DB"));

        Map<String, Object> emailMap = setting.getEmailSetting();

        this.adminMail = (String) emailMap.getOrDefault(
                "adminSenderMail",
                "admin@example.com"
        );
        this.adminSubject = (String) emailMap.getOrDefault(
                "adminSubject",
                "Admin Subject"
        );
        this.claimantSubject = (String) emailMap.getOrDefault(
                "claimantSubject",
                "Claimant Subject"
        );
        this.respondantSubject = (String) emailMap.getOrDefault(
                "respondentSubject",
                "Respondent Subject"
        );
    }


    private boolean sendMail(String recipient, String subject, String body) {
        try {
            JavaMailSender javaMailSender = getJavaMailSenderFromSettings();
            MimeMessage mimeMessage = getJavaMailSenderFromSettings().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(adminMail);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
