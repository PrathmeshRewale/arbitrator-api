package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.PaymentMode;
import com.mac.arbitrator.entity.enums.PaymentStatus;

public record CreateRazorpayCallbackRequest(
        String razorpay_order_id,
        String razorpay_payment_id,
        String razorpay_signature,
        Long paymentId,
        String userEmail,
        String paymentMode,
        String paidByUserEmail,
        Float amount,
        String transactionId
) {
}
