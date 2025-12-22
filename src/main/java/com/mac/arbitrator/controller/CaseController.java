package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseHearingScheduleRequest;
import com.mac.arbitrator.dto.request.create.CreateCaseRequest;
import com.mac.arbitrator.dto.request.create.CreatePartyDocumentRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseDocumentRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseMiniRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseRequest;
import com.mac.arbitrator.dto.response.CaseDetailResponseDto;
import com.mac.arbitrator.dto.response.CaseResponseDto;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.service.CaseHearingSchedulerService;
import com.mac.arbitrator.service.CaseService;
import com.mac.arbitrator.service.ClaimantCaseDocumentsService;
import com.mac.arbitrator.service.RespondantCaseDocumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/apiv1/case")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;
    private final ClaimantCaseDocumentsService claimantCaseDocumentsService;
    private final RespondantCaseDocumentsService respondantCaseDocumentsService;
    private final CaseHearingSchedulerService caseHearingSchedulerService;

    @GetMapping("all/{userId}")
    public ResponseEntity<?> getAllCaseByUserId(@PathVariable Long userId){
        return new ResponseEntity<List<CaseResponseDto>>(caseService.getAllCases(userId), HttpStatus.OK);
    }

    @GetMapping("check_by_admissionId/{id}")
    public ResponseEntity<?> checkIfCaseAlreadyExistByAdmissionId(@PathVariable Long id){
        return new ResponseEntity<Boolean>(caseService.checkIfCaseWithAdmissionIdAlreadyExist(id), HttpStatus.OK);
    }

    @GetMapping("all/{userId}/{type}")
    public ResponseEntity<?> getAllCaseByUserIdAndByType(@PathVariable Long userId,@PathVariable String type){
        return new ResponseEntity<List<CaseResponseDto>>(caseService.getAllCasesByUserIdAndType(userId, type), HttpStatus.OK);
    }

    @GetMapping("allByType/{type}")
    public ResponseEntity<?> getAllCaseAndByType(@PathVariable String type){
        return new ResponseEntity<List<CaseResponseDto>>(caseService.getAllCasesByType(type), HttpStatus.OK);
    }

    @GetMapping("getdetail/{id}")
    public ResponseEntity<?> getAllCaseAndByType(@PathVariable Long id){
        return new ResponseEntity<CaseResponseDto>(caseService.getByCaseId(id), HttpStatus.OK);
    }

    @GetMapping("{caseId}")
    public ResponseEntity<?> getCaseDetailsByCaseId(@PathVariable Long caseId){
        return new ResponseEntity<CaseDetailResponseDto>(caseService.getCaseDetailsByCaseId(caseId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCase(@RequestBody CreateCaseRequest createCaseRequest){
        return new ResponseEntity<GenericResponseDto>(caseService.registerCase(createCaseRequest), HttpStatus.OK);
    }

    @GetMapping("/exists/{userId}")
    public ResponseEntity<GenericResponseDto> checkIfCaseExistsByUserId(@PathVariable Long userId) {
        GenericResponseDto response = caseService.getCaseByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add_claimant_case_document")
    public ResponseEntity<GenericResponseDto> addClaimantCaseDocument(@RequestBody CreatePartyDocumentRequest createPartyDocumentRequest) {
        return new ResponseEntity<>(claimantCaseDocumentsService.addDocument(createPartyDocumentRequest), HttpStatus.OK);
    }

    @PostMapping("/add_respondant_case_document")
    public ResponseEntity<GenericResponseDto> addRespondantCaseDocument(@RequestBody CreatePartyDocumentRequest createPartyDocumentRequest) {
        return new ResponseEntity<>(respondantCaseDocumentsService.addDocument(createPartyDocumentRequest), HttpStatus.OK);
    }

    @PutMapping("/update/{caseId}")
    public ResponseEntity<?> Update(@PathVariable Long caseId, @RequestBody UpdateCaseRequest updateCaseRequest){
        return new ResponseEntity<GenericResponseDto>(caseService.updateCaseDeatils(caseId,updateCaseRequest), HttpStatus.OK);
    }

    @PutMapping("/updatemini/{id}")
    public ResponseEntity<?> UpdateCaseMini(@PathVariable Long id,@RequestBody UpdateCaseMiniRequest updateCaseMiniRequest){
        return new ResponseEntity<GenericResponseDto>(caseService.updateCaseStatusAndNumber(id,updateCaseMiniRequest), HttpStatus.OK);
    }

    @PutMapping("/updatecasedoucumet/{id}")
    public ResponseEntity<?> UpdateCaseDocument(@PathVariable Long id,@RequestBody UpdateCaseDocumentRequest updateCaseDocumentRequest){
        return new ResponseEntity<GenericResponseDto>(caseService.updateCaseDocument(id,updateCaseDocumentRequest), HttpStatus.OK);
    }

    @PostMapping("/add_case_hearing")
    public ResponseEntity<?> AddCaseHearingSchedule(@RequestBody CreateCaseHearingScheduleRequest createCaseHearingScheduleRequest){
        return new ResponseEntity<GenericResponseDto>(caseHearingSchedulerService.createCaseHearing(createCaseHearingScheduleRequest), HttpStatus.OK);
    }

}
