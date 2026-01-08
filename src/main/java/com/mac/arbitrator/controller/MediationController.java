/**
 * -----------------------------------------------------------------------------
 * Author      : Prathmesh Rewale (https://github.com/prathmeshrewale)
 * Date        : 2025-09-01
 * Company     : Webzworld
 * File        : MediationController.java
 * Purpose     : Creates MediationController for the application , handles mediation form related requests
 * -----------------------------------------------------------------------------
 */
package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateMediationRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateMediationFormStatus;
import com.mac.arbitrator.dto.response.MediationResponseDto;
import com.mac.arbitrator.service.MediationFormService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/mediation")
public class MediationController {

    private final MediationFormService mediationFormService;

    public MediationController(MediationFormService mediationFormService) {
        this.mediationFormService = mediationFormService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMediations(){
        return new ResponseEntity<>( mediationFormService.getAllMediations(), HttpStatus.OK);
    }

    @GetMapping("/mediations")
    public Page<MediationResponseDto> getAllMediations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return mediationFormService.getAllMediations(page, size);
    }


    @GetMapping("/mediation_form/{id}")
    public ResponseEntity<?> getMediationById(@PathVariable Long id){
        MediationResponseDto mediationResponseDto = mediationFormService.getMediationById(id);
        if(mediationResponseDto != null){
            return new ResponseEntity<>(mediationResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponseDto("Error", "Mediation not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMediation(@RequestBody CreateMediationRequestDto createMediationRequestDto){
        return new ResponseEntity<>(mediationFormService.createMediation(createMediationRequestDto), HttpStatus.OK);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateMediation(@PathVariable Long id, @RequestBody CreateMediationRequestDto createMediationRequestDto){
//        return new ResponseEntity<>(mediationFormService.updateMediation(id, createMediationRequestDto), HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMediation(@PathVariable Long id){
        return new ResponseEntity<>(mediationFormService.deleteMediation(id), HttpStatus.OK);
    }


}
