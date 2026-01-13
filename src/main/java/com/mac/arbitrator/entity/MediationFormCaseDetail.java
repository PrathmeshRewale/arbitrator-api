package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mediation_form_case_detail",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class MediationFormCaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Long mediationFormId;

    @Column(nullable = true)
    private String recordingLink;

    @Column(nullable = false)
    private LocalDateTime dateOfHearing;

    @Column(nullable = false)
    private String zoomLink;
}
