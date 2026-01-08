/**
 * -----------------------------------------------------------------------------
 * Author      : Prathmesh Rewale (https://github.com/prathmeshrewale)
 * Date        : 2025-09-01
 * Company     : Webzworld
 * File        : AdmissionController.java
 * Purpose     : Creates AdmissionController for the application , handles admission form related requests
 * -----------------------------------------------------------------------------
 */
package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.service.AdmissionFormService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

    private final AdmissionFormService admissionFormService;

    public AdmissionController(AdmissionFormService admissionFormService) {
        this.admissionFormService = admissionFormService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmissions(){
        return new ResponseEntity<>( admissionFormService.getAllAdmissions(), HttpStatus.OK);
    }

    @GetMapping("/admissions")
    public Page<AdmissionResponseDto> getAllAdmissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return admissionFormService.getAllAdmissions(page, size);
    }


    @GetMapping("/admission_form/{id}")
    public ResponseEntity<?> getAdmissionById(@PathVariable Long id){
        AdmissionResponseDto admissionResponseDto = admissionFormService.getAdmissionById(id);
        if(admissionResponseDto != null){
            return new ResponseEntity<>(admissionResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponseDto("Error", "Admission not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmission(@RequestBody CreateAdmissionRequestDto createAdmissionRequestDto){
        return new ResponseEntity<>(admissionFormService.createAdmission(createAdmissionRequestDto), HttpStatus.OK);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateAdmission(@PathVariable Long id, @RequestBody CreateAdmissionRequestDto createAdmissionRequestDto){
//        return new ResponseEntity<>(admissionFormService.updateAdmission(id, createAdmissionRequestDto), HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmission(@PathVariable Long id){
        return new ResponseEntity<>(admissionFormService.deleteAdmission(id), HttpStatus.OK);
    }


}
