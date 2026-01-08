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
@Table(name = "legal_case_respondant_document",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class LegalCaseRespondantDocument {
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
    private Long respondantId;

    @Column(nullable = false)
    private String respondantName;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;
}
