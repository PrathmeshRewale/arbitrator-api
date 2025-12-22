package com.mac.arbitrator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class ClaimantCaseDocuments {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long caseId;
    private Long claimantId;
    @Column(nullable = false)
    private String documentTitle;
    @Column(nullable = false)
    private String documentUrl;
    private Instant uploadedAt;
    @Column(nullable = false)
    private String uploadedBy;
}
