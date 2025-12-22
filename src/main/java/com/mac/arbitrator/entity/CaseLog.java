package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long caseId;
    @Column(nullable = false)
    private String arbitrator;
    @Column(nullable = false)
    private LocalDate lastHearingDate;
    @Column(nullable = false)
    private LocalDate nextHearingDate;
    @Column(nullable = false)
    private String purposeOfHearing;
    @Column(nullable = false)
    private String attachment;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Long createdById;
    @Column(nullable = false)
    private String createdByName;
}
