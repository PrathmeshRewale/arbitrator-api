package com.mac.arbitrator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpRequestDto {
    private String username;
    private String userEmail;
    private int otp;
}
