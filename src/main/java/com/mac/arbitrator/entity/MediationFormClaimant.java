package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mediation_form_claimant",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class MediationFormClaimant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admission_form_id", nullable = false)
    private Long mediationFormId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "party_type_id", nullable = false)
    private Long partyTypeId;

    @Column(name = "party_type_name", nullable = false)
    private String partyTypeName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "state_id", nullable = false)
    private Long stateId;

    @Column(name = "state_name", nullable = false)
    private String stateName;

    @Column(name = "city_id", nullable = false)
    private Long cityId;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "address", nullable = false)
    private String address;

    private String secondaryEmail;

    @Column(name = "secondary_country_id")
    private Long secondaryCountryId;

    @Column(name = "secondary_country_name")
    private String secondaryCountryName;

    @Column(name = "secondary_state_id")
    private Long secondaryStateId;

    @Column(name = "secondary_state_name")
    private String secondaryStateName;

    @Column(name = "secondary_city_id")
    private Long secondaryCityId;

    @Column(name = "secondary_city_name")
    private String secondaryCityName;

    @Column(name = "secondary_zip_code")
    private String secondaryZipCode;

    @Column(name = "secondary_address")
    private String secondaryAddress;
}
