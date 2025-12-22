package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseLogRequest;

public interface CaseLogService {
    GenericResponseDto create(CreateCaseLogRequest createCaseLogRequest);
}
