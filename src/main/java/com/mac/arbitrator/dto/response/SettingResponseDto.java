package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettingResponseDto {
    private Long id;
    private Map<String, Object> emailSetting;
    private Map<String, Object> schedularSetting;
}
