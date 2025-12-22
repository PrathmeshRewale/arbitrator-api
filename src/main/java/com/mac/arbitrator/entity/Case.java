package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "legal_case")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String caseNo;
    @Column(nullable = false)
    private Long admissionFormId;
    @Column(nullable = false)
    private String section17DocPath;
    @Column(nullable = false)
    private String statementOfClaimPath;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false , columnDefinition = "VARCHAR(20) DEFAULT 'DRAFT'")
    private AdmissionFormStatus status;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Long createdById;
    @Column(nullable = false)
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;
}
