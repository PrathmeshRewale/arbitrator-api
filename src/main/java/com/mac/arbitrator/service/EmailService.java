package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.request.EmailRequest;

public interface EmailService {
    boolean sendClaimantMail(EmailRequest emailRequest);
    boolean sendRespondentMail(EmailRequest emailRequest);
    boolean sendAdminMail(EmailRequest emailRequest);
    boolean sendUserMail(EmailRequest emailRequest);
    String getAdminReceiverEmail();
}
