package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreatePartyDocumentRequest;
import com.mac.arbitrator.entity.ClaimantCaseDocuments;
import com.mac.arbitrator.repository.ClaimantCaseDocumentsRepository;
import com.mac.arbitrator.service.ClaimantCaseDocumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ClaimantCaseDocumentsServiceImpl implements ClaimantCaseDocumentsService {

    private final ClaimantCaseDocumentsRepository claimantCaseDocumentsRepository;

    @Override
    public GenericResponseDto addDocument(CreatePartyDocumentRequest createPartyDocumentRequest) {
        claimantCaseDocumentsRepository.saveAndFlush(ClaimantCaseDocuments.builder()
                        .caseId(createPartyDocumentRequest.caseId())
                        .claimantId(createPartyDocumentRequest.partyId())
                        .uploadedAt(Instant.now())
                        .documentTitle(createPartyDocumentRequest.documentTitle())
                        .documentUrl(createPartyDocumentRequest.documentUrl())
                        .uploadedBy(createPartyDocumentRequest.uploadedBy())
                .build());

        return new GenericResponseDto("success", "Claimant case document added successfully");
    }
}
