package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.request.SettingRequest;
import com.mac.arbitrator.dto.response.SettingResponse;
import com.mac.arbitrator.entity.Setting;
import com.mac.arbitrator.repository.SettingRepository;
import com.mac.arbitrator.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {


    private final SettingRepository settingRepository;
    private final ObjectMapper objectMapper;

    @Override
    public SettingResponse getSettings() {
        Setting setting = settingRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No settings found"));

        return mapToSettingResponse(setting);
    }

    @Override
    public SettingResponse updateSettings(SettingRequest request) {
        Setting setting = settingRepository.findAll().stream().findFirst()
                .orElse(new Setting());

        setting.setEmailSetting(request.getEmailSetting());
        setting.setSchedularSetting(request.getSchedularSetting());

        Setting saved = settingRepository.save(setting);
        return mapToSettingResponse(saved);
    }

    private SettingResponse mapToSettingResponse(Setting setting) {
        Map<String, Object> emailMap;
        Map<String, Object> schedularMap;

        try {
            emailMap = objectMapper.readValue(setting.getEmailSetting(), Map.class);
        } catch (JsonMappingException e) {
            emailMap = Map.of("error", "Invalid JSON for emailSetting");
        } catch (JsonProcessingException e) {
            emailMap = Map.of("error", "Invalid JSON for emailSetting");
        }

        try {
            schedularMap = objectMapper.readValue(setting.getSchedularSetting(), Map.class);
        } catch (JsonMappingException e) {
            schedularMap = Map.of("error", "Invalid JSON for schedularSetting");
        } catch (JsonProcessingException e) {
            schedularMap = Map.of("error", "Invalid JSON for schedularSetting");
        }

        return new SettingResponse(
                setting.getId(),
                emailMap,
                schedularMap
        );
    }
}
