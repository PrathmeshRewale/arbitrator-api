package com.mac.arbitrator.dto.request.create;

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
public class CreatePaymentUserTypeRequestDto {
    private Long paymentId;
    private String userEmail;
    private String paidByUserEmail;
    private Float amount;
    private String transactionId;
    private String paymentMode;
}
