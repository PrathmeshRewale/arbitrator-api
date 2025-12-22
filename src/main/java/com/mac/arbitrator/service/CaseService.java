package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseDocumentRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseMiniRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseRequest;
import com.mac.arbitrator.dto.response.CaseDetailResponseDto;
import com.mac.arbitrator.dto.response.CaseResponseDto;

import java.util.List;

public interface CaseService {
    GenericResponseDto registerCase(CreateCaseRequest createCaseRequest);
    List<CaseResponseDto> getAllCases(Long userid);
    GenericResponseDto getCaseByUserId(Long userid);
    CaseDetailResponseDto getCaseDetailsByCaseId(Long caseId);
    GenericResponseDto updateCaseDeatils(Long caseId, UpdateCaseRequest updateCaseRequest);
    List<CaseResponseDto> getAllCasesByUserIdAndType(Long userId, String type);
    List<CaseResponseDto> getAllCasesByType(String type);
    GenericResponseDto updateCaseStatusAndNumber(Long id, UpdateCaseMiniRequest updateCaseMiniRequest);
    GenericResponseDto updateCaseDocument(Long id, UpdateCaseDocumentRequest updateCaseDocumentRequest);
    Boolean checkIfCaseWithAdmissionIdAlreadyExist(Long id);
    CaseResponseDto getByCaseId(Long id);
}
