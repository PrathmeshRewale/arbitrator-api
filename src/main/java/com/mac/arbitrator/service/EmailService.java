package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;

public interface EmailService {
    boolean sendClaimantMail(CreateEmailRequestDto emailRequest);
    boolean sendRespondentMail(CreateEmailRequestDto emailRequest);
    boolean sendAdminMail(CreateEmailRequestDto emailRequest);
    boolean sendSystemMail(CreateEmailRequestDto createEmailRequestDto);
    String getAdminReceiverEmail();
}
