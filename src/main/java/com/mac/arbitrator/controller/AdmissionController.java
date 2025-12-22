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
import com.mac.arbitrator.dto.request.AdmissionPaymentRequest;
import com.mac.arbitrator.dto.request.AdmissionRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.service.AdmissionFormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/apiv1/admission")
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

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllAdmissionsByUserId(@PathVariable Long userId){
        return new ResponseEntity<>( AdmissionFormService.getAllAdmissions(userId), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmission(@PathVariable Long id, @RequestBody AdmissionRequestDto admissionRequestDto){
        return new ResponseEntity<>(AdmissionFormService.updateAdmission(id, admissionRequestDto), HttpStatus.OK);
    }

    @PutMapping("/update_status/{userId}/{admissionId}/{status}")
    public ResponseEntity<?> updateAdmission(@PathVariable Long userId, @PathVariable Long admissionId,@PathVariable String status){
        return new ResponseEntity<>(AdmissionFormService.updateAdmissionStatus(userId,admissionId,status), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmission(@PathVariable Long id){
        return new ResponseEntity<>(AdmissionFormService.deleteAdmission(id), HttpStatus.OK);
    }

    @GetMapping("/case_register/{admissionId}/{userEmail}")
    public ResponseEntity<?> getAdmissionDetailsForRegisterCase(@PathVariable Long admissionId, @PathVariable String userEmail){
        AdmissionResponseDto admissionResponseDto = AdmissionFormService.getAdmissionDetailsByIdAndUserEmail(admissionId,userEmail);
        if(admissionResponseDto != null){
            return new ResponseEntity<>(admissionResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponseDto("Error", "Admission not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admission_payment")
    public ResponseEntity<?> createAdmissionWithUser(@RequestBody AdmissionPaymentRequest admissionRequestDto){
        return new ResponseEntity<>(AdmissionFormService.updateAdmissionAmountAndStatus(admissionRequestDto), HttpStatus.OK);
    }

}
