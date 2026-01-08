package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.PaymentUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentUserRepository extends JpaRepository<PaymentUser, Long> {
}