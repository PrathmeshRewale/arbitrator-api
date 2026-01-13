package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByRemainingAmountGreaterThan(float v);
}