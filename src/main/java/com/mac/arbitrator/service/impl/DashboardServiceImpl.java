package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.response.DashboardCountResponseDto;
import com.mac.arbitrator.dto.response.DashboardRecentActivityResponseDto;
import com.mac.arbitrator.dto.response.UpcomingHearingDetailsResponseDto;
import com.mac.arbitrator.entity.AdmissionForm;
import com.mac.arbitrator.entity.CaseHearingSchedule;
import com.mac.arbitrator.entity.CaseLog;
import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CaseRepository caseRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final UserRepository userRepository;
    private final CaseHearingSchedulerRepository caseHearingSchedulerRepository;
    private final CaseLogRepository caseLogRepository;

    @Override
    public DashboardCountResponseDto getDashboardCount() {
        int newAdmission = admissionFormRepository.findAll().stream().filter(obj->obj.getStatus() != AdmissionFormStatus.APPROVED).toArray().length;
        int totalCases = caseRepository.findAll().size();
        int totalUsers = userRepository.findAll().size();
        int totalAdvocate = userRepository.findAll().stream().filter(obj->obj.getBarRegistrationNumber()!=null).toList().size();

        return DashboardCountResponseDto.builder()
                .totalNewAdmission(newAdmission)
                .totalCases(totalCases)
                .activeUser(totalUsers)
                .totalAdvocates(totalAdvocate)
                .build();
    }

    @Override
    public List<UpcomingHearingDetailsResponseDto> getAllUpcomingHearingDetails() {

        return caseHearingSchedulerRepository.findAll().stream().map(obj->{
            return  UpcomingHearingDetailsResponseDto.builder()
                    .nextHearing(obj.getNextHearing())
                    .caseId(obj.getCaseId())
                    .status(obj.getStatus().toString())
                    .build();
        }).toList();
    }

    @Override
    public List<DashboardRecentActivityResponseDto> getDashboardRecentActivityResponsea() {

        List<DashboardRecentActivityResponseDto> response = new ArrayList<>();

        /* ===================== ADMISSIONS ===================== */
        response.addAll(
                admissionFormRepository.findAll().stream()
                        .filter(obj -> obj != null && obj.getStatus() != AdmissionFormStatus.APPROVED)
                        .sorted(
                                Comparator.comparing(
                                        AdmissionForm::getCreatedAt,
                                        Comparator.nullsLast(Comparator.naturalOrder())
                                ).reversed()
                        )
                        .limit(5)
                        .map(obj -> DashboardRecentActivityResponseDto.builder()
                                .date(
                                        obj.getCreatedAt() != null
                                                ? obj.getCreatedAt()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                                : null
                                )
                                .type("ADMISSION")
                                .id(String.valueOf(obj.getId()))
                                .details("Admission with details " + obj.getId())
                                .build()
                        )
                        .toList()
        );

        /* ===================== USERS ===================== */
        response.addAll(
                userRepository.findAll().stream()
                        .filter(Objects::nonNull)
                        .sorted(
                                Comparator.comparing(
                                        User::getCreatedAt,
                                        Comparator.nullsLast(Comparator.naturalOrder())
                                )
                        )
                        .limit(5)
                        .map(obj -> DashboardRecentActivityResponseDto.builder()
                                .date(
                                        obj.getCreatedAt() != null
                                                ? obj.getCreatedAt()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                                : null
                                )
                                .type("USERS")
                                .id(String.valueOf(obj.getId()))
                                .details("USER with details " + obj.getId())
                                .build()
                        )
                        .toList()
        );

        /* ===================== CASE LOGS ===================== */
        response.addAll(
                caseLogRepository.findAll().stream()
                        .filter(Objects::nonNull)
                        .sorted(
                                Comparator.comparing(
                                        CaseLog::getCreatedAt,
                                        Comparator.nullsLast(Comparator.naturalOrder())
                                )
                        )
                        .limit(5)
                        .map(obj -> DashboardRecentActivityResponseDto.builder()
                                .date(
                                        obj.getCreatedAt() != null
                                                ? obj.getCreatedAt()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate()
                                                : null
                                )
                                .type("CASE_LOG")
                                .id(String.valueOf(obj.getCaseId()))
                                .details("Case with details " + obj.getCaseId())
                                .build()
                        )
                        .toList()
        );

        return response;
    }


}
