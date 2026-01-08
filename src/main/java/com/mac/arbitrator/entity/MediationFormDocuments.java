package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mediation_form_documents")
public class MediationFormDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mediation_form_id", nullable = false)
    private Long mediationFormId;

    @Column(name = "poa_loa_idcard", nullable = true)
    private String poaLoaIdCard;

    @Column(name = "lrn_demand_notice", nullable = true)
    private String lrnDemandNotice;

    @Column(name = "agreement_contract", nullable = false)
    private String agreementContract;
}
