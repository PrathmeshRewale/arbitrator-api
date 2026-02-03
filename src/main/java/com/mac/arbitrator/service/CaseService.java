package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseRespondantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseClaimantDocumentRequestDto;
import org.springframework.data.domain.Page;

public interface CaseService {
    GenericResponseDto createLegalCaseClaimantDocument(CreateLegalCaseClaimantDocumentRequestDto createLegalCaseClaimantDocumentRequestDto);
    GenericResponseDto createLegalCaseRespondantDocument(CreateLegalCaseRespondantDocumentRequestDto createLegalCaseRespondantDocumentRequestDto);
    GenericResponseDto updateLegalCaseDocument(Long caseId, UpdateLegalCaseClaimantDocumentRequestDto updateLegalCaseClaimantDocumentRequestDto);
}
