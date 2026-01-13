package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "legal_case_log",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class LegalCaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long arbitratorId;

    @Column(nullable = false)
    private String arbitratorName;

    @Column(nullable = false)
    private Long legalCaseId;

    @Column(nullable = false)
    private LocalDate lastHearingDate;

    @Column(nullable = false)
    private LocalDate nextHearingDate;

    @Column(nullable = false)
    private String purposeOfHearing;

    @Column(nullable = true)
    private String recordingLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HearingStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Long createdById;

    @Column(nullable = false)
    private String createdByName;
}
