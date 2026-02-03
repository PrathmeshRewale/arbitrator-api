package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseLogRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseRespondantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseStatusRequestDto;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.service.LegalCaseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/legal_case")
public class LegalCaseController {

    private final LegalCaseService legalCaseService;

    public LegalCaseController(LegalCaseService legalCaseService) {
        this.legalCaseService = legalCaseService;
    }

    @PostMapping("/claimant_document")
    public ResponseEntity<?> createClaimantDocument(@RequestBody CreateLegalCaseClaimantDocumentRequestDto dto) {
        return ResponseEntity.ok(legalCaseService.createLegalCaseClaimantDocument(dto));
    }

    @PostMapping("/respondant_document")
    public ResponseEntity<?> createRespondantDocument(@RequestBody CreateLegalCaseRespondantDocumentRequestDto dto) {
        return ResponseEntity.ok(legalCaseService.createLegalCaseRespondantDocument(dto));
    }

    @PutMapping("/document/{caseId}")
    public ResponseEntity<?> updateLegalCaseDocument(
            @PathVariable Long caseId,
            @RequestBody UpdateLegalCaseClaimantDocumentRequestDto dto) {
        return ResponseEntity.ok(legalCaseService.updateLegalCaseDocument(caseId, dto));
    }

    @PostMapping("/log")
    public ResponseEntity<?> createLegalCaseLog(@RequestBody CreateLegalCaseLogRequestDto dto) {
        return ResponseEntity.ok(legalCaseService.createLegalCaseLog(dto));
    }

    @GetMapping("/check_admin_approval/{caseId}")
    public ResponseEntity<?> checkIfApprovedByAdmin(@PathVariable Long caseId) {
        return ResponseEntity.ok(legalCaseService.checkIfLegalCaseIsApprovedByAdmin(caseId));
    }

    @GetMapping("/legal_case/{caseId}")
    public ResponseEntity<?> getLegalCaseMiniDetailsByCaseId(@PathVariable Long caseId) {
        return ResponseEntity.ok(legalCaseService.getLegalCaseMniDetailByLegalCaseId(caseId));
    }


    @PutMapping("/status/arbitrator")
    public ResponseEntity<?> updateStatusByArbitrator(
            @RequestBody UpdateLegalCaseStatusRequestDto dto) {
        return ResponseEntity.ok(legalCaseService.updatedLegalCaseStatusByArbitrator(dto));
    }

    @GetMapping("/by_user_type")
    public ResponseEntity<?> getAllLegalCaseByUserType(
            @RequestParam UserCaseType userCaseType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                legalCaseService.getAllLegalCaseByUserType(PageRequest.of(page, size), userCaseType)
        );
    }

    @GetMapping("/by_userid_and_type")
    public ResponseEntity<?> getAllLegalCaseByUserIdUserType(
            @RequestParam UserCaseType userCaseType,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                legalCaseService.getAllLegalCaseByUserIdAndUserType(PageRequest.of(page, size), userId, userCaseType)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllLegalCase(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                legalCaseService.getAllLegalCase(PageRequest.of(page, size))
        );
    }

    @GetMapping("arbitrator_legal_case")
    public ResponseEntity<?> getAllArbitratorLegalCase(
            @RequestParam Long arbitratorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                legalCaseService.getAllLegalCaseByArbitratorId(PageRequest.of(page, size),arbitratorId)
        );
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<?> getLegalCaseDetail(@PathVariable Long caseId) {
        return ResponseEntity.ok(legalCaseService.getLegalCaseDetailByLegalCaseId(caseId));
    }
}

