package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreatePartyDocumentRequest;

public interface RespondantCaseDocumentsService {
    GenericResponseDto addDocument(CreatePartyDocumentRequest createPartyDocumentRequest);
}
