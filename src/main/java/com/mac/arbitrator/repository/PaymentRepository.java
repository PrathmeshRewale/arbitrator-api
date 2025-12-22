package com.mac.arbitrator.repository;

import com.mac.arbitrator.dto.response.PendingPaymentResponseDto;
import com.mac.arbitrator.entity.Payment;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByAdmissionId(Long admissionId);
    @Query("SELECT p FROM Payment p WHERE p.admissionId in :admissionId")
    List<Payment> findByAllAdmissionId(List<Long> admissionId);


//@Query("""
////        SELECT new com.mac.arbitrator.dto.response.PendingPaymentResponseDto(
////        p.id,
////        p.admissionId,
////        p.userEmail,
////        p.amount,
////        p.paymentStatus,
////        p.userType,
////        p.createdAt
////        )
////        FROM Payment p
////        WHERE p.paymentStatus = com.mac.arbitrator.entity.enums.PaymentStatus.UNPAID
////        """)
////    List<PendingPaymentResponseDto> getPendingPayments();

    Payment findByCaseId(Long caseId);
    Payment findByAdmissionId(Long id);
}