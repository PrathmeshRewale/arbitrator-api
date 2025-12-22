package com.mac.arbitrator.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingRequest {
    private String emailSetting;       // JSON string
    private String schedularSetting;   // JSON string
}
