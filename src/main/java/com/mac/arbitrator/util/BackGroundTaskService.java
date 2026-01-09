package com.mac.arbitrator.util;

import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.CaseStatus;
import com.mac.arbitrator.repository.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BackGroundTaskService {

    private final LegalCaseRepository legalCaseRepository;
    private final ArbitratorRepository arbitratorRepository;
    private final ArbitratorLegalCaseRepository arbitratorLegalCaseRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final AdmissionFormClaimantRepository admissionFormClaimantRepository;

    public BackGroundTaskService(LegalCaseRepository legalCaseRepository, ArbitratorRepository arbitratorRepository, ArbitratorLegalCaseRepository arbitratorLegalCaseRepository, AdmissionFormRepository admissionFormRepository, AdmissionFormClaimantRepository admissionFormClaimantRepository) {
        this.legalCaseRepository = legalCaseRepository;
        this.arbitratorRepository = arbitratorRepository;
        this.arbitratorLegalCaseRepository = arbitratorLegalCaseRepository;
        this.admissionFormRepository = admissionFormRepository;
        this.admissionFormClaimantRepository = admissionFormClaimantRepository;
    }

    @Async
    public void createCaseAndAssignArbitrators(Long admissionId) {

        // Fetch the admission form
        AdmissionForm admissionForm = admissionFormRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission with id -> " + admissionId + " not found"));

        // Fetch all claimants for this admission
        List<AdmissionFormClaimant> claimants = admissionFormClaimantRepository.findByAdmissionFormId(admissionId);

        // Fetch all arbitrators for this jurisdiction
        List<Arbitrator> arbitrators = arbitratorRepository.findByJurisdictionId(admissionForm.getJurisdictionId());

        // Loop through each claimant
        for (AdmissionFormClaimant claimant : claimants) {
            String claimantEmail = claimant.getEmail();

            // Loop through each arbitrator
            for (Arbitrator arbitrator : arbitrators) {

                // Count how many cases this arbitrator already has for this claimant
                long caseCount = arbitratorLegalCaseRepository.countByClaimantEmailAndArbitratorId(claimantEmail, arbitrator.getId());

                if (caseCount < 3) {
                    // Create a new legal case
                    LegalCase legalCase = new LegalCase();
                    legalCase.setCreatedAt(Instant.now());
                    legalCase.setCreatedByName("SYSTEM");
                    legalCase.setStatus(CaseStatus.DRAFT);
                    legalCase.setAdmissionFormId(admissionId);

                    LegalCase legalCase1 = legalCaseRepository.save(legalCase);

                    ArbitratorLegalCase arbitratorLegalCase = new ArbitratorLegalCase();
                    arbitratorLegalCase.setArbitratorId(arbitrator.getId());
                    arbitratorLegalCase.setLegalCaseId(legalCase1.getId());
                    arbitratorLegalCase.setClaimantEmail(claimantEmail);

                    arbitratorLegalCaseRepository.save(arbitratorLegalCase);

                    // Break after assigning to one arbitrator, so no single claimant gets multiple cases to the same arbitrator
                    break;
                }
            }
        }
    }

}
