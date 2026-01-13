package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.PaymentUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentUserRepository extends JpaRepository<PaymentUser, Long> {
    PaymentUser findByPaymentIdAndUserEmail(Long paymentId, String userEmail);
    List<PaymentUser> findByPaymentId(Long id);
}