package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdmissionFormUserRequestDto {
    private String fullName;
    private String userEmail;
    private String phoneNo;
    private Long admissionId;
    private String username;
    private String password;
}
