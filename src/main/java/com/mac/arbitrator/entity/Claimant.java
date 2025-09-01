package com.mac.arbitrator.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "claimant",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class Claimant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admission_form_id", nullable = false)
    private Long admissionFormId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

}
