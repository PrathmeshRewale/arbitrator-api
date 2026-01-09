package com.mac.arbitrator.util;

import com.mac.arbitrator.entity.enums.UserCaseType;

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
        String arbitrationLink = "https://app.mac.org.in/webapp/admission/payment/" + admissionId +"/"+ userEmail;

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
        String arbitrationLink = "https://app.mac.org.in/webapp/mediation/payment/" + mediationId +"/"+ userEmail;

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

}
