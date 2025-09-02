/**
 * -----------------------------------------------------------------------------
 * Author      : Prathmesh Rewale (https://github.com/prathmeshrewale)
 * Date        : 2025-09-01
 * Company     : Webzworld
 * File        : AdmissionFormDocuments.java
 * Purpose     : Creates AdmissionFormDocuments Entity for the application
 * -----------------------------------------------------------------------------
 */
package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admission_form_documents")
public class AdmissionFormDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "admission_form_id", nullable = false)
    private Long admissionFormId;

    @Column(name = "poa_loa_idcard", nullable = false)
    private String poaLoaIdCard;

    @Column(name = "lrn_demand_notice", nullable = false)
    private String lrnDemandNotice;

    @Column(name = "agreement_contract", nullable = false)
    private String agreementContract;

    @Column(name= "orders", nullable = false)
    private String orders;


}