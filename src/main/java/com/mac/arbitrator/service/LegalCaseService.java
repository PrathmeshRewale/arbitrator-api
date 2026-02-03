package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseLogRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseRespondantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseStatusRequestDto;
import com.mac.arbitrator.dto.response.LegalCaseResponseDto;
import com.mac.arbitrator.dto.response.mini.LegalCaseMniResponseDto;
import com.mac.arbitrator.entity.enums.UserCaseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface LegalCaseService {
    GenericResponseDto createLegalCaseClaimantDocument(CreateLegalCaseClaimantDocumentRequestDto createLegalCaseClaimantDocumentRequestDto);
    GenericResponseDto createLegalCaseRespondantDocument(CreateLegalCaseRespondantDocumentRequestDto createLegalCaseRespondantDocumentRequestDto);
    GenericResponseDto updateLegalCaseDocument(Long caseId, UpdateLegalCaseClaimantDocumentRequestDto updateLegalCaseClaimantDocumentRequestDto);
    GenericResponseDto createLegalCaseLog(CreateLegalCaseLogRequestDto createLegalCaseLogRequestDto);
    GenericResponseDto checkIfLegalCaseIsApprovedByAdmin(Long caseId);
    GenericResponseDto updatedLegalCaseStatusByArbitrator(UpdateLegalCaseStatusRequestDto updateLegalCaseStatusRequestDto);
    Page<LegalCaseMniResponseDto> getAllLegalCaseByUserType(Pageable pageable, UserCaseType userCaseType);
    Page<LegalCaseMniResponseDto> getAllLegalCase(Pageable pageable);
    LegalCaseResponseDto getLegalCaseDetailByLegalCaseId(Long caseId);
    Page<LegalCaseMniResponseDto> getAllLegalCaseByUserIdAndUserType(PageRequest of, Long userId, UserCaseType userCaseType);
    Page<LegalCaseMniResponseDto> getAllLegalCaseByArbitratorId(PageRequest pageRequest, Long arbitratorId);
    LegalCaseMniResponseDto getLegalCaseMniDetailByLegalCaseId(Long caseId);
}
