package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.request.EmailRequest;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.SettingRepository;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.utils.MailUtil;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final SettingRepository settingRepository;
    private final ObjectMapper objectMapper;

    private String adminMail;
    private String adminSubject;
    private String claimantSubject;
    private String respondantSubject;


    public static String generateRegistrationEmail(String fullName, Long admissionId) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String admissionLink = "https://app.mac.org.in/webapp/admission/" + admissionId;

        return """
           <!DOCTYPE html>
           <html>
           <head>
             <meta charset="UTF-8">
             <title>New Admission Notification</title>
           </head>
           <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
             <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
               <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
                 <h2 style="margin: 0;">New Admission Added</h2>
               </div>
               <p>Dear <strong>%s</strong>,</p>
               <p>We would like to inform you that a new admission has been successfully added to the system.</p>
               <p>You can click the button below to view the details of the new admission.</p>
               <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; margin-top: 20px;">
                 View Admission Details
               </a>
               <p style="margin-top: 30px;">Best regards,<br>
               <strong>%s</strong><br>
               Admissions Team</p>
               <div style="font-size: 12px; color: #999; margin-top: 30px;">
                 &copy; %d %s. All rights reserved.
               </div>
             </div>
           </body>
           </html>
    """.formatted(fullName, admissionLink, companyName, year, companyName);
    }

//    public static String generateRespondentEmail(String respondentName, Long admissionId, double amountToPay) {
//        String companyName = "Mac Legal Portal";
//        int year = java.time.Year.now().getValue();
//        String registrationLink = "http://app.mac.org.in/auth/register";
//
//        return """
//        <!DOCTYPE html>
//        <html>
//        <head>
//            <meta charset="UTF-8">
//            <title>Case Notification</title>
//        </head>
//        <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
//            <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
//                        box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
//                <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
//                    <h2 style="margin: 0;">New Case Filed Against You</h2>
//                </div>
//                <p>Dear <strong>%s</strong>,</p>
//                <p>We would like to inform you that a new case has been filed where you are listed as a <strong>Respondent</strong>.</p>
//                <p>Before you can register to view or respond to this case, you are required to complete a payment of
//                <strong>₹%.2f</strong>.</p>
//                <p>Once the payment is completed, you can register and access the case details using the link below:</p>
//                <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #dc3545; color: white;
//                                    text-decoration: none; border-radius: 4px; margin-top: 20px;">
//                    Register & View Case
//                </a>
//                <p style="margin-top: 30px;">Your Admission ID: <strong>%d</strong></p>
//                <p style="margin-top: 30px;">If you have already completed the payment, please proceed with registration.</p>
//                <p style="margin-top: 30px;">Regards,<br>
//                <strong>%s</strong><br>
//                Legal Case Management Team</p>
//                <div style="font-size: 12px; color: #999; margin-top: 30px;">
//                    &copy; %d %s. All rights reserved.
//                </div>
//            </div>
//        </body>
//        </html>
//    """.formatted(respondentName, amountToPay, registrationLink, admissionId, companyName, year, companyName);
//    }


    public static String generateCaseRejectionEmail(String fullName, Long caseId, Long admissionId, String rejectionReason) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/webapp/case/" + caseId;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Case Document Rejected</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0; color: #dc3545;">Case Document Rejected</h2>
           </div>
           <p>Dear <strong>%s</strong>,</p>
           <p>
             The documents submitted for <strong>Case No. %d</strong> related to 
             <strong>Admission ID %d</strong> have been reviewed by the administrator.
           </p>
           <p>
             Unfortunately, the submitted documents are <strong>not proper or incomplete</strong> and the case has been rejected at this time.
           </p>
           <p><strong>Reason for Rejection:</strong></p>
           <p style="background-color: #f8d7da; padding: 10px; border-radius: 4px; color: #721c24;">
             %s
           </p>
           <p>
             Please update the case with the correct and complete information and resubmit the documents for approval.
           </p>
           <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; margin-top: 20px;">
             Update Case Details
           </a>
           <p style="margin-top: 30px;">Best regards,<br>
           <strong>%s</strong><br>
           Case Administration Team</p>
           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>
         </div>
       </body>
       </html>
    """.formatted(
                fullName,
                caseId,
                admissionId,
                rejectionReason,
                caseLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateCaseApprovedForHearingEmail(
            String fullName,
            String caseId,
            Long admissionId,
            UserCaseType type   // Claimant or Respondent
    ) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/webapp/case/" + caseId;

        String roleMessage = type == UserCaseType.CLAIMANT
                ? "You are listed as the <strong>Claimant</strong> in this case."
                : "You are listed as the <strong>Respondent</strong> in this case.";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Case Approved and Proceeding to Hearing</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0; color: #28a745;">Case Approved</h2>
           </div>

           <p>Dear <strong>%s</strong>,</p>

           <p>
             We are pleased to inform you that the case related to
             <strong>Admission ID %d</strong> and <strong>Case No. %s</strong>
             has been successfully reviewed and approved by the administrator.
           </p>

           <p>%s</p>

           <p>
             The case has now been moved forward and will proceed to the
             <strong>hearing stage</strong>. Further hearing details will be
             communicated to you shortly.
           </p>

           <p>
             Please log in to the system to view the case details and stay
             updated on future proceedings.
           </p>

           <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; margin-top: 20px;">
             View Case Details
           </a>

           <p style="margin-top: 30px;">
             Best regards,<br>
             <strong>%s</strong><br>
             Case Management Team
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>
         </div>
       </body>
       </html>
    """.formatted(
                fullName,
                admissionId,
                caseId,
                roleMessage,
                caseLink,
                companyName,
                year,
                companyName
        );
    }


    public static String generateCaseApprovalAdminEmail(String fullName, Long caseId) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/webapp/cases/" + caseId;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>New Case Created for Approval</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">New Case Created for Verification</h2>
           </div>
           <p>Dear <strong>%s</strong>,</p>
           <p>We are writing to inform you that a new case has been created in the system. Please verify the attached document and approve the case if everything is in order.</p>
           <p>You can click the button below to review and approve the case details.</p>
           <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; margin-top: 20px;">
             View and Approve Case
           </a>
           <p style="margin-top: 30px;">Best regards,<br>
           <strong>%s</strong><br>
           Case Management Team</p>
           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>
         </div>
       </body>
       </html>
    """.formatted(fullName, caseLink, companyName, year, companyName);
    }

    public static String generateRespondentEmail(String respondentName, Long admissionId) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String registrationLink = "https://app.mac.org.in/auth/register";

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>New Case Notification</title>
    </head>
    <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
        <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
            <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
                <h2 style="margin: 0;">New Case Filed Against You</h2>
            </div>

            <p>Dear <strong>%s</strong>,</p>

            <p>
                We would like to inform you that a <strong>new legal case</strong> has been created
                in which you are listed as a <strong>Respondent</strong>.
            </p>

            <p>
                To view the case details and take necessary action, please sign up or log in to the portal.
            </p>

            <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #dc3545; color: white;
                                text-decoration: none; border-radius: 4px; margin-top: 20px;">
                Sign Up / View Case
            </a>

            <p style="margin-top: 30px;">
                Your <strong>Admission ID</strong>: <strong>%d</strong>
            </p>

            <p style="margin-top: 20px;">
                Please keep this Admission ID for future reference while accessing the case.
            </p>

            <p style="margin-top: 30px;">Regards,<br>
            <strong>%s</strong><br>
            Legal Case Management Team</p>

            <div style="font-size: 12px; color: #999; margin-top: 30px;">
                &copy; %d %s. All rights reserved.
            </div>
        </div>
    </body>
    </html>
    """.formatted(respondentName, registrationLink, admissionId, companyName, year, companyName);
    }


//    public static String generateClaimantEmail(String claimantName, Long admissionId, double amountToPay) {
//        String companyName = "Mac Legal Portal";
//        int year = java.time.Year.now().getValue();
//        String paymentLink = "http://app.mac.org.in/webapp/payment/" + admissionId;
//
//        return """
//        <!DOCTYPE html>
//        <html>
//        <head>
//            <meta charset="UTF-8">
//            <title>Case Submission Confirmation</title>
//        </head>
//        <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
//            <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
//                        box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
//                <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
//                    <h2 style="margin: 0;">Your Case Has Been Submitted</h2>
//                </div>
//                <p>Dear <strong>%s</strong>,</p>
//                <p>Thank you for submitting your case through the <strong>%s</strong>.
//                Your case has been successfully created and is now pending payment confirmation.</p>
//                <p>To proceed, please complete the required payment of <strong>₹%.2f</strong>.
//                Once payment is received, our legal team will begin reviewing your submission.</p>
//                <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #28a745; color: white;
//                                    text-decoration: none; border-radius: 4px; margin-top: 20px;">
//                    Complete Payment
//                </a>
//                <p style="margin-top: 30px;">Your Admission ID: <strong>%d</strong></p>
//                <p style="margin-top: 30px;">You can use this Admission ID to track your case status after payment.</p>
//                <p style="margin-top: 30px;">Best regards,<br>
//                <strong>%s</strong><br>
//                Case Management Team</p>
//                <div style="font-size: 12px; color: #999; margin-top: 30px;">
//                    &copy; %d %s. All rights reserved.
//                </div>
//            </div>
//        </body>
//        </html>
//    """.formatted(claimantName, companyName, amountToPay, paymentLink, admissionId, companyName, year, companyName);
//    }

    public static String generateClaimantEmail(String claimantName, Long admissionId) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String loginLink = "https://app.mac.org.in/auth/register";

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Case Created Successfully</title>
    </head>
    <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
        <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
            <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
                <h2 style="margin: 0;">Your Case Has Been Created</h2>
            </div>

            <p>Dear <strong>%s</strong>,</p>

            <p>
                Your legal case has been <strong>successfully created</strong> on the
                <strong>%s</strong>.
            </p>

            <p>
                You can sign up or log in to the portal to view case details,
                track status, and receive further updates.
            </p>

            <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #28a745; color: white;
                                text-decoration: none; border-radius: 4px; margin-top: 20px;">
                Login & View Case
            </a>

            <p style="margin-top: 30px;">
                Your <strong>Admission ID</strong>: <strong>%d</strong>
            </p>

            <p style="margin-top: 20px;">
                Please keep this Admission ID for future communication and reference.
            </p>

            <p style="margin-top: 30px;">Best regards,<br>
            <strong>%s</strong><br>
            Case Management Team</p>

            <div style="font-size: 12px; color: #999; margin-top: 30px;">
                &copy; %d %s. All rights reserved.
            </div>
        </div>
    </body>
    </html>
    """.formatted(claimantName, companyName, loginLink, admissionId, companyName, year, companyName);
    }


    public static String generateClaimantPaymentReminderEmail(String claimantName, Long admissionId, double amountToPay) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String paymentLink = "https://app.mac.org.in/webapp/cases/claimant";

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Payment Reminder</title>
    </head>
    <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
        <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
            <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
                <h2 style="margin: 0; color: #dc3545;">Payment Reminder</h2>
            </div>
            <p>Dear <strong>%s</strong>,</p>
            <p>We noticed that the payment for your case submitted through <strong>%s</strong> has not yet been completed. 
            Your case is currently pending payment confirmation.</p>
            <p>To proceed with the legal process, please make the required payment of <strong>₹%.2f</strong> at your earliest convenience.</p>
            <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #dc3545; color: white;
                                text-decoration: none; border-radius: 4px; margin-top: 20px;">
                Complete Payment
            </a>
            <p style="margin-top: 30px;">Your Admission ID: <strong>%d</strong></p>
            <p style="margin-top: 20px;">Please note: Your Admission ID is required to track the status of your case.</p>
            <p style="margin-top: 30px;">If you have already made the payment, please disregard this email. Otherwise, we encourage you to complete the payment promptly to avoid delays in processing your case.</p>
            <p style="margin-top: 30px;">Best regards,<br>
            <strong>%s</strong><br>
            Case Management Team</p>
            <div style="font-size: 12px; color: #999; margin-top: 30px;">
                &copy; %d %s. All rights reserved.
            </div>
        </div>
    </body>
    </html>
    """.formatted(claimantName, companyName, amountToPay, paymentLink, admissionId, companyName, year, companyName);
    }

    public static String generateRespondentPaymentReminderEmail(String respondentName, Long admissionId, double amountToPay) {
        String companyName = "Mac Legal Portal";
        int year = 2025;
        String paymentLink = "https://app.mac.org.in/webapp/cases/respondant";

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Payment Reminder</title>
    </head>
    <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
        <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
            <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
                <h2 style="margin: 0; color: #dc3545;">Payment Reminder</h2>
            </div>
            <p>Dear <strong>%s</strong>,</p>
            <p>We noticed that the payment required to register as a respondent for the case with Admission ID <strong>%d</strong> has not yet been completed.</p>
            <p>The payment amount is <strong>₹%.2f</strong>. Completing this payment allows you to register and access the case details:</p>
            <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #dc3545; color: white;
                                text-decoration: none; border-radius: 4px; margin-top: 20px;">
                Complete Payment & Register
            </a>
            <p style="margin-top: 30px;">Please note: If you have already completed the payment, you can proceed directly with registration.</p>
            <p style="margin-top: 30px;">Best regards,<br>
            <strong>%s</strong><br>
            Legal Case Management Team</p>
            <div style="font-size: 12px; color: #999; margin-top: 30px;">
                &copy; %d %s. All rights reserved.
            </div>
        </div>
    </body>
    </html>
    """.formatted(respondentName, admissionId, amountToPay, paymentLink, companyName, year, companyName);
    }

    public static String generateForgotPasswordOtpEmail(
            String fullName,
            Integer otp
    ) {
        String companyName = "Mac Legal Portal";
        int year = 2025;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Password Reset OTP</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">
           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">Password Reset OTP</h2>
           </div>

           <p>Dear <strong>%s</strong>,</p>

           <p>
             We received a request to reset the password for your account.
             Please use the following One-Time Password (OTP) to proceed.
           </p>

           <div style="font-size: 22px; font-weight: bold; letter-spacing: 3px; background-color: #f1f5f9; padding: 15px; text-align: center; border-radius: 6px; margin: 20px 0;">
             %s
           </div>

           <p>
             This OTP is valid for a limited time only. Do not share this OTP
             with anyone for security reasons.
           </p>

           <p>
             If you did not request a password reset, please ignore this email.
             Your account will remain secure.
           </p>

           <p style="margin-top: 30px;">
             Best regards,<br>
             <strong>%s</strong><br>
             Support Team
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>
         </div>
       </body>
       </html>
    """.formatted(
                fullName,
                otp,
                companyName,
                year,
                companyName
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

    /** Send email to Claimant */
    public boolean sendClaimantMail(EmailRequest emailRequest) {
        return sendMail(emailRequest.getRecipient(), claimantSubject, emailRequest.getMsgBody());
    }

    /** Send email to Respondent */
    public boolean sendRespondentMail(EmailRequest emailRequest) {
        return sendMail(emailRequest.getRecipient(), respondantSubject, emailRequest.getMsgBody());
    }

    /** Optionally, send admin notification */
    public boolean sendAdminMail(EmailRequest emailRequest) {
        return sendMail(emailRequest.getRecipient(), adminSubject, emailRequest.getMsgBody());
    }

    @Override
    public boolean sendUserMail(EmailRequest emailRequest) {
        return sendMail(emailRequest.getRecipient(), "MAC credentials", emailRequest.getMsgBody());
    }

    @Override
    public String getAdminReceiverEmail() {
        var settingOpt = settingRepository.findAll().stream().findFirst();
        if (settingOpt.isEmpty()) {
            throw new RuntimeException("No email settings found in DB");
        }

        String emailJson = settingOpt.get().getEmailSetting();
        Map<String, Object> emailMap;
        try {
            emailMap = objectMapper.readValue(emailJson, Map.class);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Invalid email settings JSON", e);
        }

        return (String) emailMap.getOrDefault("adminRecieverMail","example.gmail.com");
    }

    private JavaMailSender getJavaMailSenderFromSettings() {
        // 1️⃣ Fetch email settings JSON from repository
        String emailJson = settingRepository.findAll().get(0).getEmailSetting();

        Map<String, Object> emailMap;
        try {
            emailMap = objectMapper.readValue(emailJson, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid email settings JSON", e);
        }

        // 2️⃣ Extract essential values
        String host = (String) emailMap.getOrDefault("host", "localhost");
        int port = (emailMap.get("port") instanceof Integer) ? (Integer) emailMap.get("port") : 25;
        String username = (String) emailMap.getOrDefault("username", "");
        String password = (String) emailMap.getOrDefault("password", "");

        return MailUtil.getMailSender(host,port,username,password);
    }

    @PostConstruct
    private void initEmailSettings() {
        var settingOpt = settingRepository.findAll().stream().findFirst();
        if (settingOpt.isEmpty()) {
            throw new RuntimeException("No email settings found in DB");
        }

        String emailJson = settingOpt.get().getEmailSetting();
        Map<String, Object> emailMap;
        try {
            emailMap = objectMapper.readValue(emailJson, Map.class);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Invalid email settings JSON", e);
        }

        this.adminMail = (String) emailMap.getOrDefault("adminSenderMail", "admin@example.com");
        this.adminSubject = (String) emailMap.getOrDefault("adminSubject", "Admin Subject");
        this.claimantSubject = (String) emailMap.getOrDefault("claimantSubject", "Claimant Subject");
        this.respondantSubject = (String) emailMap.getOrDefault("respondentSubject", "Respondent Subject");
    }
}
