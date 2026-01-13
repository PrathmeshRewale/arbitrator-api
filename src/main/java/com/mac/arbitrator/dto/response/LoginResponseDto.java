package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String username;
    private Long userId;
    private Long arbitratorId;
    private String userEmail;
    private Long roleId;
    private String roleName;
    private Map<String,Object> permissions;
}
