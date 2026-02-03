package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.update.UpdateSettingRequestDto;
import com.mac.arbitrator.service.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/setting")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSetting(@PathVariable Long id, @RequestBody UpdateSettingRequestDto updateSettingRequestDto){
        return ResponseEntity.ok(settingService.updateSetting(id,updateSettingRequestDto));
    }

    @GetMapping()
    public ResponseEntity<?> getAllSetting(){
        return ResponseEntity.ok(settingService.getAllSetting());
    }
}
