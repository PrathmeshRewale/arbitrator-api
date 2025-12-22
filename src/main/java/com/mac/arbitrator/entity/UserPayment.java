package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.PaymentMode;
import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.SettingRepository;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_payment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long paymentId;
    private Long paidById;
    private String paidByName;
    private String userEmail;
    @Enumerated(EnumType.STRING)
    private UserCaseType userCaseType;
    private Float amount;
    private String transactionId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private PaymentMode paymentMode;
}
