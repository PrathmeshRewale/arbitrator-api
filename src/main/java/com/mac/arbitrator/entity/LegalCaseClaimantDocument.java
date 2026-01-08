package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "legal_case_claimant_document",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class LegalCaseClaimantDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long legalCaseId;

    @Column(nullable = false)
    private String documentTitle;

    @Column(nullable = false)
    private String documentUrl;

    @Column(nullable = false)
    private Long claimantId;

    @Column(nullable = false)
    private String claimantName;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;
}
