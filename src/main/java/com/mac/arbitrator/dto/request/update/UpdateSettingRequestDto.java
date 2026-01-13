package com.mac.arbitrator.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSettingRequestDto {
    private Map<String,Object> emailSetting;
    private Map<String,Object> schedularSetting;
}
