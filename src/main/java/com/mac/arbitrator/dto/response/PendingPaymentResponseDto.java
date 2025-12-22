package com.mac.arbitrator.dto.response;


import com.mac.arbitrator.entity.Payment;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingPaymentResponseDto {
    private Long paymentId;
    private UserCaseType userCaseType;
    private String userEmail;
    private Float amount;
    private PaymentStatus paymentStatus;
}
