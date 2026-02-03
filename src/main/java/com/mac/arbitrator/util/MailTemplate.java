package com.mac.arbitrator.util;

import com.mac.arbitrator.entity.enums.UserCaseType;

import java.time.LocalDateTime;

public final class MailTemplate {
    private MailTemplate() {} // prevent instantiation
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

    public static String generateLoginOtpEmail(
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
        <title>Login OTP</title>
    </head>
    <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
        <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

            <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
                <h2 style="margin: 0;">Login Verification Code</h2>
            </div>

            <p>Dear <strong>%s</strong>,</p>

            <p>
                We detected a login attempt to your <strong>%s</strong> account.
                Please use the One-Time Password (OTP) below to complete your login.
            </p>

            <div style="font-size: 22px; font-weight: bold; letter-spacing: 4px;
                        background-color: #f1f5f9; padding: 15px; text-align: center;
                        border-radius: 6px; margin: 20px 0;">
                %s
            </div>

            <p>
                This OTP is valid for a short period. For your security,
                please do not share this code with anyone.
            </p>

            <p>
                If you did not attempt to log in, please ignore this email.
                No further action is required.
            </p>

            <p style="margin-top: 30px;">
                Regards,<br>
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
                companyName,
                otp,
                companyName,
                year,
                companyName
        );
    }

    public static String generateAdmissionAdminEmail(Long admissionId) {

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

           <p>
             A new admission has been successfully added to the system.
           </p>

           <p>
             Click the button below to view the admission details.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #007bff; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               View Admission Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             Best regards,<br>
             <strong>%s</strong><br>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                admissionLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationAdminEmail(Long mediationId) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String mediationLink = "https://app.mac.org.in/webapp/mediation/" + mediationId;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>New Mediation Notification</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">New Mediation Added</h2>
           </div>

           <p>
             A new mediation has been successfully added to the system.
           </p>

           <p>
             Click the button below to view the mediation details.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #007bff; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               View Mediation Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             Best regards,<br>
             <strong>%s</strong><br>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                mediationLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateAdmissionFormPaymentEmail(
            Long admissionId,
            String userEmail,
            UserCaseType partyRole,        // "Claimant" or "Respondent"
            Float paymentAmount     // e.g. "₹5,000"
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String arbitrationLink = "https://app.mac.org.in/auth/admission/payment/" + admissionId +"/"+ userEmail;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Payment Notification</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff;
                     border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                     padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">Payment Notification</h2>
           </div>

           <p>
             This is to inform you that a payment has been recorded in the arbitration system.
           </p>

           <p>
             <strong>Your Role:</strong> %s<br>
             <strong>Payment Amount:</strong> %s
           </p>

           <p>
             Please click the button below to view the payment details.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #28a745; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               View Payment Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             If you have any questions regarding this payment, please contact the arbitration center.
           </p>

           <p style="margin-top: 20px;">
             Best regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                partyRole,
                paymentAmount,
                arbitrationLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationFormPaymentEmail(
            Long mediationId,
            String userEmail,
            UserCaseType partyRole,        // "Claimant" or "Respondent"
            Float paymentAmount     // e.g. "₹5,000"
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String arbitrationLink = "https://app.mac.org.in/auth/mediation/payment/" + mediationId +"/"+ userEmail;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Payment Notification</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff;
                     border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                     padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">Payment Notification</h2>
           </div>

           <p>
             This is to inform you that a payment has been recorded in the arbitration system.
           </p>

           <p>
             <strong>Your Role:</strong> %s<br>
             <strong>Payment Amount:</strong> %s
           </p>

           <p>
             Please click the button below to view the payment details.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #28a745; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               View Payment Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             If you have any questions regarding this payment, please contact the arbitration center.
           </p>

           <p style="margin-top: 20px;">
             Best regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                partyRole,
                paymentAmount,
                arbitrationLink,
                companyName,
                year,
                companyName
        );
    }


    // email for arbitration request rejection notification
    public static String generateArbitrationRejectedEmail(
            String jurisdiction,
            String defaultClause,
            String arbitrationClause,
            String reliefSought,
            String disputeDate,
            String disputeAmount,
            String requestDateOfForm,
            String rejectionReason
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Arbitration Request Rejected</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                     box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0; color: #dc3545;">Arbitration Request Rejected</h2>
           </div>

           <p>
             Your arbitration request has been <strong style="color:#dc3545;">rejected</strong>.
             Please review the request details and rejection reason below.
           </p>

           <table style="width: 100%%; border-collapse: collapse; margin-top: 15px; font-size: 14px;">

             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Jurisdiction:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr>
               <td style="padding: 8px; font-weight: bold;">Default Clause:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Arbitration Clause:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr>
               <td style="padding: 8px; font-weight: bold;">Relief Sought:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Dispute Date:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr>
               <td style="padding: 8px; font-weight: bold;">Dispute Amount:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Request form date:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr>
               <td style="padding: 8px; font-weight: bold; color:#dc3545;">Reason for Rejection:</td>
               <td style="padding: 8px; color:#dc3545;">%s</td>
             </tr>

           </table>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                jurisdiction,
                defaultClause,
                arbitrationClause,
                reliefSought,
                disputeDate,
                disputeAmount,
                requestDateOfForm,
                rejectionReason,
                companyName,
                year,
                companyName
        );
    }


    // email for case closed notification
    public static String generateCaseClosedEmail(
            String admissionFormNo,
            String arbitratorName,
            String arbitratorMobile,
            String claimants,
            String respondents
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String loginLink = "https://app.mac.org.in/auth/login";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Case Closed Notification</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                     box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0; color: #dc3545;">Case Closed Successfully</h2>
           </div>

           <p>
             The following arbitration case has been <strong>successfully closed</strong>.
             Please review the case summary and related details below.
           </p>

           <table style="width: 100%%; border-collapse: collapse; margin-top: 15px; font-size: 14px;">
             <tr>
               <td style="padding: 8px; font-weight: bold;">Admission No:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Assigned Arbitrator:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr>
               <td style="padding: 8px; font-weight: bold;">Arbitrator Contact:</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Claimant(s):</td>
               <td style="padding: 8px;">%s</td>
             </tr>

             <tr>
               <td style="padding: 8px; font-weight: bold;">Respondent(s):</td>
               <td style="padding: 8px;">%s</td>
             </tr>
           </table>

           <p style="margin-top: 25px;">
             You may login to the admin portal to review the final proceedings, documents, and closure details.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #28a745; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               View Case Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                admissionFormNo,
                arbitratorName,
                arbitratorMobile,
                claimants,
                respondents,
                loginLink,
                companyName,
                year,
                companyName
        );
    }


    //    Admin view email for case created for admission form
    public static String generateAdmissionFormCaseCreatedEmailForAdmin(
            String admissionFormNo,
            String arbitratorName,
            String arbitratorMobile
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String loginLink = "https://app.mac.org.in/auth/login";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Admission Assigned Notification</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                     box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">New Admission Form Assigned to Arbitrator</h2>
           </div>

           <p>
             A new admission form has been successfully created and assigned to an arbitrator.
           </p>

           <table style="width: 100%%; border-collapse: collapse; margin-top: 15px;">
             <tr>
               <td style="padding: 8px; font-weight: bold;">Admission No:</td>
               <td style="padding: 8px;">%s</td>
             </tr>
             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Assigned Arbitrator:</td>
               <td style="padding: 8px;">%s</td>
             </tr>
             <tr>
               <td style="padding: 8px; font-weight: bold;">Contact Number Of Arbitrator:</td>
               <td style="padding: 8px;">%s</td>
             </tr>
           </table>

           <p style="margin-top: 25px;">
             Please login to the admin portal to monitor and manage this admission.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #007bff; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               Open Admin Dashboard
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                admissionFormNo,
                arbitratorName,
                arbitratorMobile,
                loginLink,
                companyName,
                year,
                companyName
        );
    }


    //    Admin view email for case created for mediation form
    public static String generateMediationFormCaseCreatedEmailForAdmin(
            String mediationFormNo,
            String arbitratorName,
            String arbitratorMobile
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String loginLink = "https://app.mac.org.in/auth/login";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Mediaition Assigned Notification</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px;
                     box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">New Mediation Form Assigned to Arbitrator</h2>
           </div>

           <p>
             A new mediation form has been successfully created and assigned to an arbitrator.
           </p>

           <table style="width: 100%%; border-collapse: collapse; margin-top: 15px;">
             <tr>
               <td style="padding: 8px; font-weight: bold;">Mediation No:</td>
               <td style="padding: 8px;">%s</td>
             </tr>
             <tr style="background-color: #f8f9fa;">
               <td style="padding: 8px; font-weight: bold;">Assigned Arbitrator:</td>
               <td style="padding: 8px;">%s</td>
             </tr>
             <tr>
               <td style="padding: 8px; font-weight: bold;">Contact Number Of Arbitrator:</td>
               <td style="padding: 8px;">%s</td>
             </tr>
           </table>

           <p style="margin-top: 25px;">
             Please login to the admin portal to monitor and manage this mediation.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #007bff; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               Open Admin Dashboard
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                mediationFormNo,
                arbitratorName,
                arbitratorMobile,
                loginLink,
                companyName,
                year,
                companyName
        );
    }


    public static String generateCaseCreatedEmailForArbitrator() {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/auth/login";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>New Case Assigned</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">New Case Created & Assigned</h2>
           </div>

           <p>
             A new arbitration case has been successfully created in the system and assigned for review.
           </p>

           <p>
             Please sign in to the portal to view complete case details, parties information, and proceedings.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #28a745; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               Login & View Case Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong><br>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                caseLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateCaseApprovedEmail(String caseNo) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/auth/login";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Case Approved</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <h2 style="color:#28a745;">Case Approved Successfully</h2>

           <p>
             Your arbitration case <strong>%s</strong> has been approved by the Arbitrator.
           </p>

           <p>
             You can now login to the portal to view complete case details, documents, and hearing schedules.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #28a745; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               Login & View Case Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong>
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(caseNo, caseLink, companyName, year, companyName);
    }


    public static String generateAdmissionFormExistingClaimantCaseEmail() {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String loginLink = "https://app.mac.org.in/auth/login";

        return """
    <!DOCTYPE html>
    <html>
    <body style="font-family: Arial; background:#f6f9fc; padding:20px;">
      <div style="max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:8px;">
        <h2>New Arbitration Case Created</h2>
        <p>Your arbitration case has been successfully created.</p>
        <p>Please login to your portal to view case details, and add all the required documents for next steps.</p>

        <div style="text-align:center; margin:25px 0;">
          <a href="%s" style="padding:12px 24px; background:#007bff; color:#fff; text-decoration:none; border-radius:6px;">
            Login & View Case
          </a>
        </div>

        <p>Regards,<br><strong>%s</strong></p>
        <small>&copy; %d %s</small>
      </div>
    </body>
    </html>
    """.formatted(loginLink, companyName, year, companyName);
    }

    public static String generateAdmissionFormExistingRespondentCaseEmail() {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String loginLink = "https://app.mac.org.in/auth/login";

        return """
    <!DOCTYPE html>
    <html>
    <body style="font-family: Arial; background:#f6f9fc; padding:20px;">
      <div style="max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:8px;">
        <h2>New Arbitration Case Assigned</h2>
        <p>You have been added as a respondent in a newly created arbitration case.</p>
        <p>Please login to review the case details and take necessary action.</p>

        <div style="text-align:center; margin:25px 0;">
          <a href="%s" style="padding:12px 24px; background:#28a745; color:#fff; text-decoration:none; border-radius:6px;">
            Login & View Case
          </a>
        </div>

        <p>Regards,<br><strong>%s</strong></p>
        <small>&copy; %d %s</small>
      </div>
    </body>
    </html>
    """.formatted(loginLink, companyName, year, companyName);
    }

    public static String generateAdmissionFormNewClaimantRegistrationEmail(Long admissionId) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String registerLink = "https://app.mac.org.in/auth/register";

        return """
    <!DOCTYPE html>
    <html>
    <body style="font-family: Arial; background:#f6f9fc; padding:20px;">
      <div style="max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:8px;">
        <h2>Registration Required – Case Created</h2>
        <p>Your payment has been successfully completed and your arbitration case is now created.</p>
        <p>Please complete your registration to access case details and proceedings.</p>
        <P>Your admission id :%d</p>

        <div style="text-align:center; margin:25px 0;">
          <a href="%s" style="padding:12px 24px; background:#ff9800; color:#fff; text-decoration:none; border-radius:6px;">
            Register & View Case
          </a>
        </div>

        <p>Regards,<br><strong>%s</strong></p>
        <small>&copy; %d %s</small>
      </div>
    </body>
    </html>
    """.formatted(admissionId,registerLink, companyName, year, companyName);
    }

    public static String generateAdmissionFormNewRespondentRegistrationEmail(Long admissionId) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String registerLink = "https://app.mac.org.in/auth/register";

        return """
    <!DOCTYPE html>
    <html>
    <body style="font-family: Arial; background:#f6f9fc; padding:20px;">
      <div style="max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:8px;">
        <h2>Registration Required – Arbitration Case</h2>
        <p>You have been added as a respondent and all payment formalities are completed.</p>
        <p>Please register to view the case, submit responses and track proceedings.</p>
        <P>Your admission id :%d</p>
        
        <div style="text-align:center; margin:25px 0;">
          <a href="%s" style="padding:12px 24px; background:#dc3545; color:#fff; text-decoration:none; border-radius:6px;">
            Register & View Case
          </a>
        </div>

        <p>Regards,<br><strong>%s</strong></p>
        <small>&copy; %d %s</small>
      </div>
    </body>
    </html>
    """.formatted(admissionId,registerLink, companyName, year, companyName);
    }

    public static String generateArbitratorMediationAssignmentEmail(
            Long mediationId
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String mediationLink = "https://app.mac.org.in/webapp/mediation/arbitrator/" + mediationId;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>New Mediation Assigned</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">New Mediation Assigned</h2>
           </div>

           <p>
             A new mediation has been submitted and you have been appointed as the arbitrator for this matter.
           </p>

           <p>
             Please review the mediation details and prepare the scheduled hearing.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #007bff; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               View Mediation Details
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong><br>
             Mediation Administration Team
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                mediationLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationFromCaseDetailClaimantEmail(
            LocalDateTime hearingDate,
            String zoomLink
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Mediation Hearing Scheduled</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">Mediation Hearing Scheduled</h2>
           </div>

           <p>
             Your mediation request has been successfully submitted and scheduled.
           </p>

           <p>
             Please find the hearing details below and join on time.
           </p>

           <p>
             <strong>Date of Hearing:</strong> %s
           </p>

           <p>
             <strong>Zoom Meeting Link:</strong><br>
             <a href="%s">%s</a>
           </p>


           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong><br>
             Mediation Support Team
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                hearingDate,
                zoomLink,
                zoomLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationFromCaseDetailRespondantEmail(
            LocalDateTime hearingDate,
            String zoomLink
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Mediation Notice</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">Mediation Notice & Hearing Schedule</h2>
           </div>

           <p>
             You have been named as a respondent in a mediation proceeding.
           </p>

           <p>
             Please review the mediation details and participate in the scheduled hearing.
           </p>

           <p>
             <strong>Date of Hearing:</strong> %s
           </p>

           <p>
             <strong>Zoom Meeting Link:</strong><br>
             <a href="%s">%s</a>
           </p>


           <p style="margin-top: 30px;">
             Regards,<br>
             <strong>%s</strong><br>
             Mediation Administration Team
           </p>

           <div style="font-size: 12px; color: #999; margin-top: 30px;">
             &copy; %d %s. All rights reserved.
           </div>

         </div>
       </body>
       </html>
    """.formatted(
                hearingDate,
                zoomLink,
                zoomLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationFromCaseDetailClaimantRecordingEmail(
            LocalDateTime hearingDate,
            String recordingLink
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Mediation Hearing Recording Available</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <h2>Mediation Hearing Completed</h2>

           <p>
             The mediation hearing scheduled on <strong>%s</strong> has been successfully completed.
           </p>

           <p>
             The arbitrator has updated the mediation form and shared the hearing recording for your reference.
           </p>

           <p>
             <strong>Recording Link:</strong><br>
             <a href="%s">%s</a>
           </p>


           <p>Regards,<br><strong>%s</strong></p>
           <small>&copy; %d %s</small>

         </div>
       </body>
       </html>
    """.formatted(
                hearingDate,
                recordingLink,
                recordingLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationFromCaseDetailRespondantRecordingEmail(
            LocalDateTime hearingDate,
            String recordingLink
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Mediation Hearing Recording Available</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <h2>Mediation Hearing Update</h2>

           <p>
             The mediation hearing held on <strong>%s</strong> has concluded.
           </p>

           <p>
             The arbitrator has updated the mediation record and the hearing recording is now available.
           </p>

           <p>
             <strong>Recording Link:</strong><br>
             <a href="%s">%s</a>
           </p>


           <p>Regards,<br><strong>%s</strong></p>
           <small>&copy; %d %s</small>

         </div>
       </body>
       </html>
    """.formatted(
                hearingDate,
                recordingLink,
                recordingLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateCaseDocumentReviewEmail(
            Long caseId
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/webapp/cases/" + caseId;

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Legal Case Documents Submitted</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0;">Case Documents Submitted for Review</h2>
           </div>

           <p>
             The claimant has submitted legal documents for the arbitration case.
           </p>

           <p>
             Kindly review the uploaded documents, including section17doc, statement, and supporting evidence,
             and proceed with the necessary verification or action.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #17a2b8; color: #ffffff;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               Login & Review Case Documents
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
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
                caseLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateCaseDocumentRejectionEmail(
            String caseNo,
            String rejectionReason
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String caseLink = "https://app.mac.org.in/webapp/cases/claimant";

        return """
       <!DOCTYPE html>
       <html>
       <head>
         <meta charset="UTF-8">
         <title>Case Document Rejected - Action Required</title>
       </head>
       <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
         <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); padding: 30px;">

           <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
             <h2 style="margin: 0; color: #dc3545;">Document Rejected – Resubmission Required</h2>
           </div>

           <p>
             The documents you submitted for your arbitration case <strong>%s</strong> have been reviewed by the administrator.
           </p>

           <p>
             Unfortunately, the submission could not be accepted and requires correction and resubmission.
           </p>

           <p>
             <strong>Reason for Rejection:</strong><br>
             <span style="color: #b30000;">%s</span>
           </p>

           <p>
             Please login to your portal, and upload the corrected documents at the earliest
             so that the proceedings may continue without delay.
           </p>

           <div style="text-align: center; margin: 25px 0;">
             <a href="%s"
                style="display: inline-block; padding: 12px 24px;
                       background-color: #ffc107; color: #000000;
                       text-decoration: none; border-radius: 6px;
                       font-weight: bold;">
               Login & Re-Submit Documents
             </a>
           </div>

           <p style="margin-top: 30px;">
             Regards,<br>
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
            caseNo,
                rejectionReason,
                caseLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateAdmissionPaymentReminderEmail(
            Long admissionId,
            String userEmail,
            UserCaseType partyRole,   // CLAIMANT / RESPONDENT
            Float remainingAmount    // e.g. 5000.00
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String paymentLink = "https://app.mac.org.in/auth/admission/payment/" + admissionId + "/" + userEmail;

        return """
   <!DOCTYPE html>
   <html>
   <head>
     <meta charset="UTF-8">
     <title>Payment Reminder</title>
   </head>
   <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
     <div style="max-width: 600px; margin: auto; background: #ffffff;
                 border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                 padding: 30px;">

       <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
         <h2 style="margin: 0; color: #d9534f;">Payment Reminder</h2>
       </div>

       <p>
         This is a gentle reminder that an outstanding payment is pending in your case.
       </p>

       <p>
         <strong>Your Role:</strong> %s<br>
         <strong>Pending Amount:</strong> ₹ %s
       </p>

       <p>
         Kindly complete the payment at your earliest convenience to avoid any delay in proceedings.
       </p>

       <div style="text-align: center; margin: 25px 0;">
         <a href="%s"
            style="display: inline-block; padding: 12px 24px;
                   background-color: #dc3545; color: #ffffff;
                   text-decoration: none; border-radius: 6px;
                   font-weight: bold;">
           Pay Now
         </a>
       </div>

       <p style="margin-top: 30px;">
         If you have already completed the payment, please ignore this message.
       </p>

       <p style="margin-top: 20px;">
         Best regards,<br>
         <strong>%s</strong>
       </p>

       <div style="font-size: 12px; color: #999; margin-top: 30px;">
         &copy; %d %s. All rights reserved.
       </div>

     </div>
   </body>
   </html>
""".formatted(
                partyRole,
                String.format("%.2f", remainingAmount),
                paymentLink,
                companyName,
                year,
                companyName
        );
    }

    public static String generateMediationPaymentReminderEmail(
            Long mediaitionId,
            String userEmail,
            UserCaseType partyRole,   // CLAIMANT / RESPONDENT
            Float remainingAmount    // e.g. 5000.00
    ) {

        String companyName = "Mac Legal Portal";
        int year = 2025;
        String paymentLink = "https://app.mac.org.in/auth/admission/payment/" + mediaitionId + "/" + userEmail;

        return """
   <!DOCTYPE html>
   <html>
   <head>
     <meta charset="UTF-8">
     <title>Payment Reminder</title>
   </head>
   <body style="font-family: Arial, sans-serif; background-color: #f6f9fc; padding: 20px; color: #333;">
     <div style="max-width: 600px; margin: auto; background: #ffffff;
                 border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);
                 padding: 30px;">

       <div style="border-bottom: 1px solid #e1e4e8; padding-bottom: 10px; margin-bottom: 20px;">
         <h2 style="margin: 0; color: #d9534f;">Payment Reminder</h2>
       </div>

       <p>
         This is a gentle reminder that an outstanding payment is pending in your case.
       </p>

       <p>
         <strong>Your Role:</strong> %s<br>
         <strong>Pending Amount:</strong> ₹ %s
       </p>

       <p>
         Kindly complete the payment at your earliest convenience to avoid any delay in proceedings.
       </p>

       <div style="text-align: center; margin: 25px 0;">
         <a href="%s"
            style="display: inline-block; padding: 12px 24px;
                   background-color: #dc3545; color: #ffffff;
                   text-decoration: none; border-radius: 6px;
                   font-weight: bold;">
           Pay Now
         </a>
       </div>

       <p style="margin-top: 30px;">
         If you have already completed the payment, please ignore this message.
       </p>

       <p style="margin-top: 20px;">
         Best regards,<br>
         <strong>%s</strong>
       </p>

       <div style="font-size: 12px; color: #999; margin-top: 30px;">
         &copy; %d %s. All rights reserved.
       </div>

     </div>
   </body>
   </html>
""".formatted(
                partyRole,
                String.format("%.2f", remainingAmount),
                paymentLink,
                companyName,
                year,
                companyName
        );
    }


}
