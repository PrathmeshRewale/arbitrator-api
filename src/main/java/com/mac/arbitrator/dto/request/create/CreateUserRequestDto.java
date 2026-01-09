package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDto {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNo;
    private String alternativeEmail;
    private String alternativePhoneNo;
    private Long roleId;
    private String roleName;
}
