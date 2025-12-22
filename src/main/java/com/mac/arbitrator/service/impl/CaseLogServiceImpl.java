package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseLogRequest;
import com.mac.arbitrator.entity.CaseLog;
import com.mac.arbitrator.exception.UserNotFoundException;
import com.mac.arbitrator.repository.CaseLogRepository;
import com.mac.arbitrator.repository.CaseRepository;
import com.mac.arbitrator.service.CaseLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CaseLogServiceImpl implements CaseLogService {
    private final CaseLogRepository caseLogRepository;
    private final CaseRepository caseRepository;
    @Override
    public GenericResponseDto create(CreateCaseLogRequest createCaseLogRequest) {
        caseRepository.findById(createCaseLogRequest.caseId()).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));
        caseLogRepository.saveAndFlush(mapToEntity(createCaseLogRequest));
        return new GenericResponseDto("success","Case log added successfully");
    }

    private CaseLog mapToEntity(CreateCaseLogRequest createCaseLogRequest) {
        return CaseLog.builder()
                .arbitrator(createCaseLogRequest.arbitrator())
                .nextHearingDate(createCaseLogRequest.lastHearingDate())
                .lastHearingDate(createCaseLogRequest.nextHearingDate())
                .purposeOfHearing(createCaseLogRequest.purposeOfHearing())
                .attachment(createCaseLogRequest.attachment())
                .caseId(createCaseLogRequest.caseId())
                .createdAt(Instant.now())
                .createdById(createCaseLogRequest.createdById())
                .createdByName(createCaseLogRequest.createdByName())
                .build();
    }

}
