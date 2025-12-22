package com.mac.arbitrator.dto.response;

import com.mac.arbitrator.entity.enums.PaymentMode;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long caseId;
    private Long admissionId;
    private Float amount;
    private Float remainingAmount;
    List<PaymentUserResponse> paymentUserResponses;

    @Data
    public class PaymentUserResponse{
        private UserCaseType type;
        private String paidByName;
        private Float amount;
        private String transactionId;
        private PaymentStatus status;
        private PaymentMode paymentMode;
        private String userEmail;
    }
}
