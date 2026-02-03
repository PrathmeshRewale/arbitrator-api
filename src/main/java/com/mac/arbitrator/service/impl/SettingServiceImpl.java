package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.update.UpdateSettingRequestDto;
import com.mac.arbitrator.dto.response.SettingResponseDto;
import com.mac.arbitrator.entity.Setting;
import com.mac.arbitrator.repository.SettingRepository;
import com.mac.arbitrator.service.SettingService;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public GenericResponseDto updateSetting(Long id,UpdateSettingRequestDto updateSettingRequestDto) {
        Setting setting = settingRepository.findById(id).orElseThrow(()->new RuntimeException("Setting with id -> " + id + " not found"));
        setting.setEmailSetting(updateSettingRequestDto.getEmailSetting());
        setting.setSchedularSetting(updateSettingRequestDto.getSchedularSetting());

        settingRepository.save(setting);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public SettingResponseDto getAllSetting() {
        return settingRepository.findAll().stream().map(obj->{
            SettingResponseDto settingResponseDto = new SettingResponseDto();
            settingResponseDto.setId(obj.getId());
            settingResponseDto.setEmailSetting(obj.getEmailSetting());
            settingResponseDto.setSchedularSetting(obj.getSchedularSetting());
            return settingResponseDto;
        }).toList().get(0);
    }
}
