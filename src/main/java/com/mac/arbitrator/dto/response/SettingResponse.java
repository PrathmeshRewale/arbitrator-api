package com.mac.arbitrator.dto.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingResponse {
    private Long id;
    private Map<String, Object> emailSetting;
    private Map<String, Object> schedularSetting;
}
