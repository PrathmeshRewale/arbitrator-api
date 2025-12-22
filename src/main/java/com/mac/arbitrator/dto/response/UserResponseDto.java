package com.mac.arbitrator.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNo;
    private Long roleId;
    private String roleName;
}