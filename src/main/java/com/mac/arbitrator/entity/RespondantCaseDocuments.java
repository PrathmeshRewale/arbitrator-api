package com.mac.arbitrator.entity;

import jakarta.persistence.*;
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
public class RespondantCaseDocuments {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long caseId;
    private Long respondantId;
    @Column(nullable = false)
    private String documentTitle;
    @Column(nullable = false)
    private String documentUrl;
    private Instant uploadedAt;
    @Column(nullable = false)
    private String uploadedBy;
}
