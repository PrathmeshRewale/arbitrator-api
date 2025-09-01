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


}