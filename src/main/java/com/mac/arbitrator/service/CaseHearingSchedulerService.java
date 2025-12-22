package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseHearingScheduleRequest;

public interface CaseHearingSchedulerService {
    GenericResponseDto createCaseHearing(CreateCaseHearingScheduleRequest createCaseHearingScheduleRequest);
}
