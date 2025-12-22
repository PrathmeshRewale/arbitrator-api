package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.SettingRequest;
import com.mac.arbitrator.dto.response.SettingResponse;
import com.mac.arbitrator.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiv1/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<SettingResponse> getSettings() {
        SettingResponse response = settingService.getSettings();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<SettingResponse> updateSettings(@RequestBody SettingRequest request) {
        SettingResponse response = settingService.updateSettings(request);
        return ResponseEntity.ok(response);
    }
}
