package com.mac.arbitrator.util;

import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.CaseStatus;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BackGroundTaskService {

    private final LegalCaseRepository legalCaseRepository;
    private final ArbitratorRepository arbitratorRepository;
    private final ArbitratorLegalCaseRepository arbitratorLegalCaseRepository;
    private final AdmissionFormRepository admissionFormRepository;
    private final AdmissionFormClaimantRepository admissionFormClaimantRepository;
    private final AdmissionFormRespondantRepository admissionFormRespondantRepository;
    private final EmailService emailService;
    private final AdmissionFormUserRepository admissionFormUserRepository;
    private final UserRepository userRepository;
    private final MediationFormRepository mediationFormRepository;
    private final MediationFormClaimantRepository mediaitonFormClaimantRepository;
    private final MediationFormArbitratorRepository mediationFormArbitratorRepository;

    public BackGroundTaskService(LegalCaseRepository legalCaseRepository, ArbitratorRepository arbitratorRepository, ArbitratorLegalCaseRepository arbitratorLegalCaseRepository, AdmissionFormRepository admissionFormRepository, AdmissionFormClaimantRepository admissionFormClaimantRepository, AdmissionFormRespondantRepository admissionFormRespondantRepository, EmailService emailService, AdmissionFormUserRepository admissionFormUserRepository, UserRepository userRepository, MediationFormRepository mediationFormRepository, MediationFormClaimantRepository mediaitonFormClaimantRepository, MediationFormArbitratorRepository mediationFormArbitratorRepository) {
        this.legalCaseRepository = legalCaseRepository;
        this.arbitratorRepository = arbitratorRepository;
        this.arbitratorLegalCaseRepository = arbitratorLegalCaseRepository;
        this.admissionFormRepository = admissionFormRepository;
        this.admissionFormClaimantRepository = admissionFormClaimantRepository;
        this.admissionFormRespondantRepository = admissionFormRespondantRepository;
        this.emailService = emailService;
        this.admissionFormUserRepository = admissionFormUserRepository;
        this.userRepository = userRepository;
        this.mediationFormRepository = mediationFormRepository;
        this.mediaitonFormClaimantRepository = mediaitonFormClaimantRepository;
        this.mediationFormArbitratorRepository = mediationFormArbitratorRepository;
    }

    @Async
    @Transactional
    public void createCaseAndAssignArbitratorForAdmissionForm(Long admissionId) {

        // 1. Fetch Admission
        AdmissionForm admissionForm = admissionFormRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission with id -> " + admissionId + " not found"));

        // 2. Check if case already exists (prevents duplicate async execution)
        Optional<LegalCase> existingCase = legalCaseRepository.findByAdmissionFormId(admissionId);
        if (existingCase.isPresent()) {
            return; // Case already created, exit safely
        }

        // 3. Fetch claimants
        List<AdmissionFormClaimant> claimants =
                admissionFormClaimantRepository.findByAdmissionFormId(admissionId);

        if (claimants.isEmpty()) {
            throw new RuntimeException("No claimants found for admission id -> " + admissionId);
        }

        String claimantEmail = claimants.get(0).getEmail(); // for load calculation

        // 4. Fetch arbitrators for jurisdiction
        List<Arbitrator> arbitrators =
                arbitratorRepository.findByJurisdictionId(admissionForm.getJurisdictionId());

        if (arbitrators.isEmpty()) {
            throw new RuntimeException("No arbitrators found for jurisdiction -> " + admissionForm.getJurisdictionId());
        }

        // 5. Find least-loaded arbitrator
        Arbitrator selectedArbitrator = null;
        long minCaseCount = Long.MAX_VALUE;

        for (Arbitrator arbitrator : arbitrators) {
            long count = arbitratorLegalCaseRepository
                    .countByClaimantEmailAndArbitratorId(claimantEmail, arbitrator.getId());

            if (count < minCaseCount) {
                minCaseCount = count;
                selectedArbitrator = arbitrator;
            }
        }

        if (selectedArbitrator == null) {
            throw new RuntimeException("No suitable arbitrator found for admission id -> " + admissionId);
        }

        // 6. Create Legal Case
        LegalCase legalCase = new LegalCase();
        legalCase.setCreatedAt(Instant.now());
        legalCase.setCreatedByName("SYSTEM");
        legalCase.setStatus(CaseStatus.DRAFT);
        legalCase.setAdmissionFormId(admissionId);

        LegalCase savedCase = legalCaseRepository.save(legalCase);

        // 7. Map Arbitrator to Case
        ArbitratorLegalCase arbitratorLegalCase = new ArbitratorLegalCase();
        arbitratorLegalCase.setArbitratorId(selectedArbitrator.getId());
        arbitratorLegalCase.setLegalCaseId(savedCase.getId());
        arbitratorLegalCase.setClaimantEmail(claimantEmail);

        arbitratorLegalCaseRepository.save(arbitratorLegalCase);

        // 8. Send Notification
        CreateEmailRequestDto email = new CreateEmailRequestDto();
        email.setMsgBody(MailTemplate.generateCaseCreatedEmailForArbitrator());
        email.setRecipient(selectedArbitrator.getEmail());
        emailService.sendSystemMail(email);
    }


    @Async
    public void AssignArbitratorForMediationForm(Long mediationFormId) {

        // Fetch the admission form
        MediationForm admissionForm = mediationFormRepository.findById(mediationFormId)
                .orElseThrow(() -> new RuntimeException("Admission with id -> " + mediationFormId + " not found"));

        // Fetch all claimants for this admission
        List<MediationFormClaimant> claimants = mediaitonFormClaimantRepository.findByMediationFormId(admissionForm.getId());

        // Fetch all arbitrators for this jurisdiction
        List<Arbitrator> arbitrators = arbitratorRepository.findByJurisdictionId(admissionForm.getJurisdictionId());

        // Loop through each claimant
        for (MediationFormClaimant claimant : claimants) {
            String claimantEmail = claimant.getEmail();

            Arbitrator arbitrator1 = RandomUtil.getRandomElement(arbitrators);

            MediationFormArbitrator mediationFormArbitrator = new MediationFormArbitrator();
            mediationFormArbitrator.setMediationFormId(mediationFormId);
            mediationFormArbitrator.setArbitratorId(arbitrator1.getId());

            mediationFormArbitratorRepository.save(mediationFormArbitrator);

            String messageBody = MailTemplate.generateArbitratorMediationAssignmentEmail(mediationFormId);

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(arbitrator1.getEmail());
            emailService.sendSystemMail(createEmailRequestDto);
        }
    }

    @Async
    public void sendClaimantAndRespondantEmailForAdmissionForm(Long admissionId){
        List<AdmissionFormClaimant> admissionFormClaimants = admissionFormClaimantRepository.findByAdmissionFormId(admissionId);
        List<AdmissionFormRespondant> admissionFormRespondants = admissionFormRespondantRepository.findByAdmissionFormId(admissionId);
        admissionFormClaimants.forEach(obj->{
            User user = userRepository.findByUsernameOrEmail("",obj.getEmail());

            List<AdmissionFormUser> admissionFormUser = new ArrayList<>();
            if(user != null) {
                admissionFormUser.addAll(admissionFormUserRepository.findByUserId(user.getId()));
            }
                if (admissionFormUser.size() > 0) {
                    CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
                    String messageBody = MailTemplate.generateAdmissionFormExistingClaimantCaseEmail();
                    createEmailRequestDto.setMsgBody(messageBody);
                    createEmailRequestDto.setRecipient(obj.getEmail());
                    emailService.sendClaimantMail(createEmailRequestDto);
                } else {
                    CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
                    String messageBody = MailTemplate.generateAdmissionFormNewClaimantRegistrationEmail(admissionId);
                    createEmailRequestDto.setMsgBody(messageBody);
                    createEmailRequestDto.setRecipient(obj.getEmail());
                    emailService.sendClaimantMail(createEmailRequestDto);
                }
        });

        admissionFormRespondants.forEach(obj->{
            User user = userRepository.findByUsernameOrEmail("",obj.getEmail());
            List<AdmissionFormUser> admissionFormUser = new ArrayList<>();
            if(user != null) {
                admissionFormUser.addAll(admissionFormUserRepository.findByUserId(user.getId()));
            }
            if(admissionFormUser.size() > 0){
                CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
                String messageBody = MailTemplate.generateAdmissionFormExistingRespondentCaseEmail();
                createEmailRequestDto.setMsgBody(messageBody);
                createEmailRequestDto.setRecipient(obj.getEmail());
                emailService.sendRespondentMail(createEmailRequestDto);
            }else {
                CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
                String messageBody = MailTemplate.generateAdmissionFormNewRespondentRegistrationEmail();
                createEmailRequestDto.setMsgBody(messageBody);
                createEmailRequestDto.setRecipient(obj.getEmail());
                emailService.sendRespondentMail(createEmailRequestDto);
            }
        });
    }



}
