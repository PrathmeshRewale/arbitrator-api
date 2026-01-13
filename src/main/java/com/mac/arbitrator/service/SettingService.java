package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.update.UpdateSettingRequestDto;
import com.mac.arbitrator.dto.response.SettingResponseDto;

public interface SettingService {
    GenericResponseDto updateSetting(Long id,UpdateSettingRequestDto updateSettingRequestDto);
    SettingResponseDto getAllSetting();
}
