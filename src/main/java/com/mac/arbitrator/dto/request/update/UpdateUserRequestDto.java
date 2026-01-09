package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserRequestDto {
    private String username;
    private String fullName;
    private String email;
    private String phoneNo;
    private String alternativeEmail;
    private String alternativePhoneNo;
    private Long roleId;
    private String roleName;
}
