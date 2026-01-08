package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdmissionRespondantRequestDto {
    private String fullName;

    private String phoneNumber;

    private Long partyTypeId;

    private String partyTypeName;

    private String email;

    private Long countryId;

    private String countryName;

    private Long stateId;

    private String stateName;

    private Long cityId;

    private String cityName;

    private String zipCode;

    private String address;

    private String secondaryEmail;

    private Long secondaryCountryId;

    private String secondaryCountryName;

    private Long secondaryStateId;

    private String secondaryStateName;

    private Long secondaryCityId;

    private String secondaryCityName;

    private String secondaryZipCode;

    private String secondaryAddress;
}
