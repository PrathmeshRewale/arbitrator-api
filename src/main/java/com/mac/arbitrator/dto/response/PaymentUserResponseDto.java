package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.PaymentMode;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentUserResponseDto {
    private Long paymentId;
    private String paidByUserEmail;
    private String userEmail;
    private UserCaseType userCaseType;
    private Float amount;
    private String transactionId;
    private PaymentStatus paymentStatus;
    private PaymentMode paymentMode;
}
