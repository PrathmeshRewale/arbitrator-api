package com.mac.arbitrator.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RespondantRequestDto {

    private Long admissionFormId;

    private String fullName;

    private String phoneNumber;

    private String address;

    private String email;

    private String country;

    private String state;

    private String city;

    private String zipCode;

}
