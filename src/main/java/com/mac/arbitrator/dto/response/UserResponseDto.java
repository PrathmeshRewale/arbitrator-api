package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNo;
    private String alternativePhoneNo;
    private String alternativeEmail;
    private Long roleId;
    private String roleName;
}
