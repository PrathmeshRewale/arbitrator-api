package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreatePartyDocumentRequest;
import com.mac.arbitrator.entity.RespondantCaseDocuments;
import com.mac.arbitrator.repository.RespondantCaseDocumentsRepository;
import com.mac.arbitrator.service.RespondantCaseDocumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RespondantCaseDocumentsServiceImpl implements RespondantCaseDocumentsService {

    private final RespondantCaseDocumentsRepository respondantCaseDocumentsRepository;

    @Override
    public GenericResponseDto addDocument(CreatePartyDocumentRequest createPartyDocumentRequest) {
        respondantCaseDocumentsRepository.saveAndFlush(RespondantCaseDocuments.builder()
                        .caseId(createPartyDocumentRequest.caseId())
                        .respondantId(createPartyDocumentRequest.partyId())
                        .documentTitle(createPartyDocumentRequest.documentTitle())
                        .documentUrl(createPartyDocumentRequest.documentUrl())
                        .uploadedAt(Instant.now())
                        .uploadedBy(createPartyDocumentRequest.uploadedBy())
                .build());
        return new GenericResponseDto("success", "Respondant case document added successfully");
    }
}
