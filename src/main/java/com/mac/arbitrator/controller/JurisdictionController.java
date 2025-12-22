package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequest;
import com.mac.arbitrator.dto.request.update.UpdateJurisdictionRequest;
import com.mac.arbitrator.service.JurisdictionService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiv1/jurisdictions")
@RequiredArgsConstructor
public class JurisdictionController {

    private final JurisdictionService jurisdictionService;

    @GetMapping("mini")
    public ResponseEntity<?> getJurisdictionMini(){
        return ResponseEntity.ok(jurisdictionService.getAllMiniJurisdiction());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJurisdictionById(@PathVariable Long id){
        return ResponseEntity.ok(jurisdictionService.getById(id));
    }

    @GetMapping()
    public ResponseEntity<?> getAllJurisdiction(){
        return ResponseEntity.ok(jurisdictionService.getAllJurisdiction());
    }

    @GetMapping("check")
    public ResponseEntity<?> checkJurisdictionNameAvailable(@PathParam("name") String name){
        return ResponseEntity.ok(jurisdictionService.checkNameAvailable(name));
    }

    @PostMapping()
    public ResponseEntity<?> createJurisdiction(@RequestBody CreateJurisdictionRequest createJurisdictionRequest){
        return ResponseEntity.ok(jurisdictionService.createJurisdiction(createJurisdictionRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatedJurisdiction(@PathVariable Long id, @RequestBody UpdateJurisdictionRequest updateJurisdictionRequest) {
        return ResponseEntity.ok(jurisdictionService.updatedJurisdiction(id,updateJurisdictionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJurisdiction(@PathVariable Long id){
        return ResponseEntity.ok(jurisdictionService.deleteJurisdiction(id));
    }

}



