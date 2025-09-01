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

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "jurdisction", nullable = false)
    private String jurdisction;

    @Column(name = "arbitration_clause", nullable = false)
    private String arbitrationClause;

    @Column(name = "refiefsought", nullable = false)
    private String refiefsought;

    @Column(name = "claim_amount", nullable = false)
    private Float claimAmount;
}
