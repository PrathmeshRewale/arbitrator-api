package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.response.dashboard.DashboardResponseDto;
import com.mac.arbitrator.dto.response.dashboard.RecentActivityDto;
import com.mac.arbitrator.dto.response.dashboard.UpcomingHearingDto;
import com.mac.arbitrator.entity.*;
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
    private final AdmissionFormUserRepository admissionFormUserRepository;


    public DashboardServiceImpl(LegalCaseRepository legalCaseRepository, AdmissionFormRepository admissionFormRepository, MediationFormRepository mediationFormRepository, LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository, ArbitratorRepository arbitratorRepository, UserRepository userRepository, ArbitratorLegalCaseRepository arbitratorLegalCaseRepository, AdmissionFormUserRepository admissionFormUserRepository) {
        this.legalCaseRepository = legalCaseRepository;
        this.admissionFormRepository = admissionFormRepository;
        this.mediationFormRepository = mediationFormRepository;
        this.legalCaseHearingScheduleRepository = legalCaseHearingScheduleRepository;
        this.arbitratorRepository = arbitratorRepository;
        this.userRepository = userRepository;
        this.arbitratorLegalCaseRepository = arbitratorLegalCaseRepository;
        this.admissionFormUserRepository = admissionFormUserRepository;
    }

    @Override
    public DashboardResponseDto getAdminDashboard() {

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

    @Override
    public DashboardResponseDto getArbitratorDashboard(Long id) {

        DashboardResponseDto dto = new DashboardResponseDto();

        // ===== Total Cases for this Arbitrator =====
        dto.setTotalCases(arbitratorLegalCaseRepository.countByArbitratorId(id));

        // ===== Get Case IDs assigned to this Arbitrator =====
        List<ArbitratorLegalCase> arbitratorCases =
                arbitratorLegalCaseRepository.findByArbitratorId(id);

        List<Long> caseIds = arbitratorCases.stream()
                .map(ArbitratorLegalCase::getLegalCaseId)
                .toList();

        // ===== Upcoming Hearings (only for this Arbitrator) =====
        List<UpcomingHearingDto> upcomingHearings =
                legalCaseHearingScheduleRepository.findAll()
                        .stream()
                        .filter(h -> caseIds.contains(h.getLegalCaseId()))
                        .map(h -> {
                            UpcomingHearingDto u = new UpcomingHearingDto();
                            u.setCaseId(h.getLegalCaseId());
                            u.setNextHearingDate(h.getNextHearingDate());

                            LegalCase legalCase =
                                    legalCaseRepository.findById(h.getLegalCaseId()).orElse(null);

                            if (legalCase != null) {
                                u.setCaseNo(legalCase.getCaseNo());
                            }

                            // Since it's this arbitrator, we can directly set name
                            Arbitrator arb =
                                    arbitratorRepository.findById(id).orElse(null);

                            if (arb != null) {
                                u.setArbitratorName(arb.getFullName());
                            }

                            return u;
                        })
                        .toList();

        dto.setUpcomingHearings(upcomingHearings);

        return dto;
    }

    @Override
    public DashboardResponseDto getUserDashboard(Long id) {

        DashboardResponseDto dto = new DashboardResponseDto();

        // ===== Step 1: Get Admission IDs for User =====
        List<AdmissionFormUser> admissionUsers =
                admissionFormUserRepository.findByUserId(id);

        List<Long> admissionIds = admissionUsers.stream()
                .map(AdmissionFormUser::getAdmissionId)
                .toList();

        if (admissionIds.isEmpty()) {
            dto.setTotalCases(0L);
            dto.setUpcomingHearings(List.of());
            return dto;
        }

        // ===== Step 2: Get Legal Cases =====
        List<LegalCase> legalCases =
                legalCaseRepository.findByAdmissionFormIdIn(admissionIds);

        dto.setTotalCases((long) legalCases.size());

        List<Long> caseIds = legalCases.stream()
                .map(LegalCase::getId)
                .toList();

        if (caseIds.isEmpty()) {
            dto.setUpcomingHearings(List.of());
            return dto;
        }

        // ===== Step 3: Get Hearings =====
        List<UpcomingHearingDto> upcomingHearings =
                legalCaseHearingScheduleRepository.findByLegalCaseIdIn(caseIds)
                        .stream()
                        .map(h -> {
                            UpcomingHearingDto u = new UpcomingHearingDto();

                            u.setCaseId(h.getLegalCaseId());
                            u.setNextHearingDate(h.getNextHearingDate());

                            // Case No
                            legalCases.stream()
                                    .filter(c -> c.getId().equals(h.getLegalCaseId()))
                                    .findFirst()
                                    .ifPresent(c -> u.setCaseNo(c.getCaseNo()));

                            // Arbitrator Name
                            ArbitratorLegalCase alc =
                                    arbitratorLegalCaseRepository.findByLegalCaseId(h.getLegalCaseId());

                            if (alc != null) {
                                Arbitrator arb =
                                        arbitratorRepository.findById(alc.getArbitratorId()).orElse(null);

                                if (arb != null) {
                                    u.setArbitratorName(arb.getFullName());
                                }
                            }

                            return u;
                        })
                        .toList();

        dto.setUpcomingHearings(upcomingHearings);

        return dto;
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
