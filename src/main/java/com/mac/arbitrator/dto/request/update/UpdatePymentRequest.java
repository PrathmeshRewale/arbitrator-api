package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.PaymentMode;
import com.mac.arbitrator.entity.enums.PaymentStatus;

public record UpdatePymentRequest(
        String userEmail,
        PaymentStatus status,
        PaymentMode paymentMode,
        String paidByName,
        Long paidById,
        String transactionId
) {
}
