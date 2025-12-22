package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentRepository extends JpaRepository<UserPayment,Long> {
    List<UserPayment> findByPaymentId(Long id);
    UserPayment findByPaymentIdAndUserEmail(Long id,String userEmail);
}
