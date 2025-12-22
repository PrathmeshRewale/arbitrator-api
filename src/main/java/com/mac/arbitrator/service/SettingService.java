package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.request.SettingRequest;
import com.mac.arbitrator.dto.response.SettingResponse;

public interface SettingService {
    SettingResponse getSettings();
    SettingResponse updateSettings(SettingRequest request);
}
