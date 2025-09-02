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
import com.mac.arbitrator.dto.request.AdmissionRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.service.AdmissionFormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

    private AdmissionFormService AdmissionFormService;

    public AdmissionController(AdmissionFormService admissionFormService) {
        AdmissionFormService = admissionFormService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmissions(){
        return new ResponseEntity<>( AdmissionFormService.getAllAdmissions(), HttpStatus.OK);
    }

    @GetMapping("/admission_form/{id}")
    public ResponseEntity<?> getAdmissionById(@PathVariable Long id){
        AdmissionResponseDto admissionResponseDto = AdmissionFormService.getAdmissionById(id);
        if(admissionResponseDto != null){
            return new ResponseEntity<>(admissionResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponseDto("Error", "Admission not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmission(@RequestBody AdmissionRequestDto admissionRequestDto){
        return new ResponseEntity<>(AdmissionFormService.createAdmission(admissionRequestDto), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmission(@PathVariable Long id, @RequestBody AdmissionRequestDto admissionRequestDto){
        return new ResponseEntity<>(AdmissionFormService.updateAdmission(id, admissionRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmission(@PathVariable Long id){
        return new ResponseEntity<>(AdmissionFormService.deleteAdmission(id), HttpStatus.OK);
    }


}
