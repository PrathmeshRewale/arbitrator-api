package com.mac.arbitrator.dto.request.create;

public record CreatePaymentRequest(
        double amount,
        String userName
) {
}
