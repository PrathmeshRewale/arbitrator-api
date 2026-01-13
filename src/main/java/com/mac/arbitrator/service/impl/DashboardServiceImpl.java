package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.response.dashboard.DashboardResponseDto;
import com.mac.arbitrator.dto.response.dashboard.RecentActivityDto;
import com.mac.arbitrator.dto.response.dashboard.UpcomingHearingDto;
import com.mac.arbitrator.entity.AdmissionForm;
import com.mac.arbitrator.entity.Arbitrator;
import com.mac.arbitrator.entity.ArbitratorLegalCase;
import com.mac.arbitrator.entity.LegalCase;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final LegalCaseRepository legalCaseRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final MediationFormRepository mediationFormRepository;
    private final LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository;
    private final ArbitratorRepository arbitratorRepository;
    private final UserRepository userRepository;
    private final ArbitratorLegalCaseRepository arbitratorLegalCaseRepository;


    public DashboardServiceImpl(LegalCaseRepository legalCaseRepository, AdmissionFormRepository admissionFormRepository, MediationFormRepository mediationFormRepository, LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository, ArbitratorRepository arbitratorRepository, UserRepository userRepository,
                                ArbitratorLegalCaseRepository arbitratorLegalCaseRepository) {
        this.legalCaseRepository = legalCaseRepository;
        this.admissionFormRepository = admissionFormRepository;
        this.mediationFormRepository = mediationFormRepository;
        this.legalCaseHearingScheduleRepository = legalCaseHearingScheduleRepository;
        this.arbitratorRepository = arbitratorRepository;
        this.userRepository = userRepository;
        this.arbitratorLegalCaseRepository = arbitratorLegalCaseRepository;
    }

    @Override
    public DashboardResponseDto getDashboardData() {

        DashboardResponseDto dto = new DashboardResponseDto();

        dto.setTotalCases(legalCaseRepository.count());
        dto.setNewAdmissions(admissionFormRepository.count());
        dto.setActiveUsers(userRepository.countByRoleNameNot("ARBITRATOR"));
        dto.setTotalArbitrators(arbitratorRepository.count());
        dto.setNewMediations(mediationFormRepository.count());

        // ===== Upcoming Hearings =====
        List<UpcomingHearingDto> upcomingHearings =
                legalCaseHearingScheduleRepository.findAll()
                        .stream()
                        .map(h -> {
                            UpcomingHearingDto u = new UpcomingHearingDto();
                            u.setCaseId(h.getLegalCaseId());
                            u.setNextHearingDate(h.getNextHearingDate());

                            LegalCase legalCase = legalCaseRepository.findById(h.getLegalCaseId()).orElse(null);
                            if (legalCase != null) {
                                u.setCaseNo(legalCase.getCaseNo());
                            }

                            ArbitratorLegalCase alc = arbitratorLegalCaseRepository.findByLegalCaseId(h.getLegalCaseId());
                            if (alc != null) {
                                Arbitrator arb = arbitratorRepository.findById(alc.getArbitratorId()).orElse(null);
                                if (arb != null) {
                                    u.setArbitratorName(arb.getFullName());
                                }
                            }
                            return u;
                        }).toList();

        dto.setUpcomingHearings(upcomingHearings);

        // ===== Recent Activities (Latest 10 by ID) =====
        List<RecentActivityDto> activities = new ArrayList<>();

        admissionFormRepository.findTop10ByOrderByIdDesc().forEach(a -> {
            RecentActivityDto r = new RecentActivityDto();
            r.setModule("ADMISSION");
            r.setTitle("Admission " + a.getAdmissionFormNo() + " created");
            activities.add(r);
        });

        mediationFormRepository.findTop10ByOrderByIdDesc().forEach(m -> {
            RecentActivityDto r = new RecentActivityDto();
            r.setModule("MEDIATION");
            r.setTitle("Mediation " + m.getMediationFormNo() + " created");
            activities.add(r);
        });

        arbitratorRepository.findTop10ByOrderByIdDesc().forEach(a -> {
            RecentActivityDto r = new RecentActivityDto();
            r.setModule("ARBITRATOR");
            r.setTitle("Arbitrator " + a.getFullName() + " registered");
            activities.add(r);
        });

        userRepository.findTop10ByOrderByIdDesc().forEach(u -> {
            RecentActivityDto r = new RecentActivityDto();
            r.setModule("USER");
            r.setTitle("User " + u.getUsername() + " created");
            activities.add(r);
        });

        dto.setRecentActivities(activities.stream().limit(10).toList());

        return dto;
    }


}
