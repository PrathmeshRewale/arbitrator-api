package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.PaymentStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(
        name = "payment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"admissionId"})
        }
)
public class Payment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long admissionId;
    private Long caseId;
    private Float amount;
    private Float remainingAmount;
    private Instant createdAt;
}
