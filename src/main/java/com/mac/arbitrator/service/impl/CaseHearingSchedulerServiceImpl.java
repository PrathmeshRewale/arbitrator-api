package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseHearingScheduleRequest;
import com.mac.arbitrator.entity.CaseHearingSchedule;
import com.mac.arbitrator.entity.enums.HearingStatus;
import com.mac.arbitrator.repository.CaseHearingSchedulerRepository;
import com.mac.arbitrator.service.CaseHearingSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaseHearingSchedulerServiceImpl implements CaseHearingSchedulerService {

    private final CaseHearingSchedulerRepository caseHearingSchedulerRepository;

    @Override
    public GenericResponseDto createCaseHearing(CreateCaseHearingScheduleRequest createCaseHearingScheduleRequest) {

        Optional<CaseHearingSchedule> optionalSchedule =
                Optional.ofNullable(caseHearingSchedulerRepository.findByCaseId(createCaseHearingScheduleRequest.caseId()));

        optionalSchedule.ifPresentOrElse(obj -> {
            obj.setCaseId(createCaseHearingScheduleRequest.caseId());
            obj.setLastHearing(obj.getNextHearing());
            obj.setNextHearing(createCaseHearingScheduleRequest.nextHearing());
            obj.setStatus(HearingStatus.valueOf(createCaseHearingScheduleRequest.status()));

            caseHearingSchedulerRepository.saveAndFlush(obj);

        }, () -> {
            // ✅ Create new record if not found
            CaseHearingSchedule newSchedule = new CaseHearingSchedule();
            newSchedule.setCaseId(createCaseHearingScheduleRequest.caseId());
            newSchedule.setNextHearing(createCaseHearingScheduleRequest.nextHearing());
            newSchedule.setLastHearing(createCaseHearingScheduleRequest.lastHearing());
            newSchedule.setStatus(HearingStatus.valueOf(createCaseHearingScheduleRequest.status()));
            caseHearingSchedulerRepository.saveAndFlush(newSchedule);
        });

        // ✅ Return a generic response
        return GenericResponseDto.builder()
                .message("Case hearing schedule processed successfully")
                .status("success")
                .build();
    }
}
