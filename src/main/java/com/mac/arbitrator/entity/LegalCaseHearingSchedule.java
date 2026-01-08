package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.HearingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "legal_case_hearing_schedule",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class LegalCaseHearingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long legalCaseId;

    @Column(nullable = false)
    private LocalDate lastHearingDate;

    @Column(nullable = false)
    private LocalDate nextHearingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HearingStatus status;
}
