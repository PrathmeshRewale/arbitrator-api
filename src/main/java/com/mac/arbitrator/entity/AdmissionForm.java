/**
 * -----------------------------------------------------------------------------
 * Author      : Prathmesh Rewale (https://github.com/prathmeshrewale)
 * Date        : 2025-09-01
 * Company     : Webzworld
 * File        : AdmissionForm.java
 * Purpose     : Creates AdmissionForm Entity for the application
 * -----------------------------------------------------------------------------
 */

package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admission_form",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class AdmissionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "default_title", nullable = false)
    private String defaultClause;

    @Column(name = "jurdisction_id", nullable = false)
    private Long jurisdictionId;

    @Column(name = "jurdisction_name", nullable = false)
    private String jurisdictionName;

    @Column(name = "arbitration_clause", nullable = false)
    private String arbitrationClause;

    @Lob
    @Column(name = "refiefsought", nullable = false,columnDefinition = "LONGTEXT")
    private String refiefSought;

    @Column(name = "claim_amount", nullable = false)
    private Float claimAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false , columnDefinition = "VARCHAR(20) DEFAULT 'DRAFT'")
    private AdmissionFormStatus status;

    private Instant createdAt;
}
