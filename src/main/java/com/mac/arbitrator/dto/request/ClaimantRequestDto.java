package com.mac.arbitrator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClaimantRequestDto {

    private Long admissionFormId;

    private String fullName;

    private String phoneNumber;

    private String address;

    private String email;

    private String country;

    private String state;

    private String city;

    private String zipCode;

    private String secondaryAddress;

    private String secondaryEmail;

    private String secondaryCountry;

    private String secondaryState;

    private String secondaryCity;

    private String secondaryZipCode;

}
