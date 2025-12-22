package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseHearingSchedule {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    private Long caseId;
    @Enumerated(EnumType.STRING)
    private HearingStatus status;
    private LocalDate lastHearing;
    private LocalDate nextHearing;
}
