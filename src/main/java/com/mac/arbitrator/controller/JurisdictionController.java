package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateJurisdictionRequestDto;
import com.mac.arbitrator.service.JurisdictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/jurisdiction")
public class JurisdictionController {

    private final JurisdictionService jurisdictionService;

    public JurisdictionController(JurisdictionService jurisdictionService) {
        this.jurisdictionService = jurisdictionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createJurisdiction(@RequestBody CreateJurisdictionRequestDto createJurisdictionRequestDto){
        return ResponseEntity.ok(jurisdictionService.create(createJurisdictionRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJurisdiction(@PathVariable Long id, @RequestBody UpdateJurisdictionRequestDto updateJurisdictionRequestDto){
        return ResponseEntity.ok(jurisdictionService.update(id,updateJurisdictionRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJurisdiction(@PathVariable Long id){
        return  ResponseEntity.ok(jurisdictionService.delete(id));
    }

    @GetMapping("/mini")
    public ResponseEntity<?> getAllJurisdictionMini(){
        return ResponseEntity.ok(jurisdictionService.getAllJurisdictionMini());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJurisdictionById(@PathVariable Long id){
        return ResponseEntity.ok(jurisdictionService.getJurisdictionById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllJurisdiction(){
        return ResponseEntity.ok(jurisdictionService.getAllJurisdiction());
    }

    @PostMapping("/bulk_insert")
    public ResponseEntity<?> createMultipleJurisdictions(@RequestBody List<CreateJurisdictionRequestDto> createJurisdictionRequestDtos){
        return ResponseEntity.ok(jurisdictionService.bulkInsert(createJurisdictionRequestDtos));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkName(@RequestParam String name){
        return ResponseEntity.ok(jurisdictionService.checkAvailability(name));
    }


}
