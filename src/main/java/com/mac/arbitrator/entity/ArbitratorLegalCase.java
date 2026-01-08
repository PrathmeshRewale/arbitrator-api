package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "arbitrator_legal_case",uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = {"legal_case_id","arbitrator_id"})
})
public class ArbitratorLegalCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long legalCaseId;

    @Column(nullable = false)
    private Long arbitratorId;

    private String claimantEmail;
}
