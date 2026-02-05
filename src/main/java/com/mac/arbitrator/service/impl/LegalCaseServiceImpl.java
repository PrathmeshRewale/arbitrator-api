package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateEmailRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseLogRequestDto;
import com.mac.arbitrator.dto.request.create.CreateLegalCaseRespondantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseClaimantDocumentRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateLegalCaseStatusRequestDto;
import com.mac.arbitrator.dto.response.*;
import com.mac.arbitrator.dto.response.mini.LegalCaseMiniResponseDto;
import com.mac.arbitrator.dto.response.mini.LegalCaseMniResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.CaseStatus;
import com.mac.arbitrator.entity.enums.HearingStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.LegalCaseService;
import com.mac.arbitrator.util.MailTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LegalCaseServiceImpl implements LegalCaseService {

    private final ArbitratorLegalCaseRepository arbitratorLegalCaseRepository;
    private final LegalCaseRepository legalCaseRepository;
    private final LegalCaseClaimantDocumentRepository legalCaseClaimantDocumentRepository;
    private final LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository;
    private final LegalCaseRespondantDocumentRepository legalCaseRespondantDocumentRepository;
    private final LegalCaseLogRepository legalCaseLogRepository;
    private final AdmissionFormUserRepository admissionFormUserRepository;
    private final EmailService emailService;
    private final ArbitratorRepository arbitratorRepository;
    private final AdmissionFormClaimantRepository admissionFormClaimantRepository;
    private final AdmissionFormRespondantRepository admissionFormRespondantRepository;
    private final ArbitratorUserRepository arbitratorUserRepository;
    private final UserRepository userRepository;
    private final AdmissionFormRepository admissionFormRepository;

    public LegalCaseServiceImpl(ArbitratorLegalCaseRepository arbitratorLegalCaseRepository, LegalCaseRepository legalCaseRepository, LegalCaseClaimantDocumentRepository legalCaseClaimantDocumentRepository, LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository, LegalCaseRespondantDocumentRepository legalCaseRespondantDocumentRepository, LegalCaseLogRepository legalCaseLogRepository, AdmissionFormUserRepository admissionFormUserRepository, EmailService emailService, ArbitratorRepository arbitratorRepository, AdmissionFormClaimantRepository admissionFormClaimantRepository, AdmissionFormRespondantRepository admissionFormRespondantRepository, ArbitratorUserRepository arbitratorUserRepository, UserRepository userRepository, AdmissionFormRepository admissionFormRepository) {
        this.arbitratorLegalCaseRepository = arbitratorLegalCaseRepository;
        this.legalCaseRepository = legalCaseRepository;
        this.legalCaseClaimantDocumentRepository = legalCaseClaimantDocumentRepository;
        this.legalCaseHearingScheduleRepository = legalCaseHearingScheduleRepository;
        this.legalCaseRespondantDocumentRepository = legalCaseRespondantDocumentRepository;
        this.legalCaseLogRepository = legalCaseLogRepository;
        this.admissionFormUserRepository = admissionFormUserRepository;
        this.emailService = emailService;
        this.arbitratorRepository = arbitratorRepository;
        this.admissionFormClaimantRepository = admissionFormClaimantRepository;
        this.admissionFormRespondantRepository = admissionFormRespondantRepository;
        this.arbitratorUserRepository = arbitratorUserRepository;
        this.userRepository = userRepository;
        this.admissionFormRepository = admissionFormRepository;
    }

    @Override
    public GenericResponseDto createLegalCaseClaimantDocument(CreateLegalCaseClaimantDocumentRequestDto createLegalCaseClaimantDocumentRequestDto) {
        LegalCaseClaimantDocument legalCaseClaimantDocument = new LegalCaseClaimantDocument();

        legalCaseClaimantDocument.setClaimantId(createLegalCaseClaimantDocumentRequestDto.getClaimantId());
        legalCaseClaimantDocument.setLegalCaseId(createLegalCaseClaimantDocumentRequestDto.getLegalCaseId());
        legalCaseClaimantDocument.setClaimantName(createLegalCaseClaimantDocumentRequestDto.getClaimantName());
        legalCaseClaimantDocument.setDocumentTitle(createLegalCaseClaimantDocumentRequestDto.getDocumentTitle());
        legalCaseClaimantDocument.setDocumentUrl(createLegalCaseClaimantDocumentRequestDto.getDocumentUrl());
        legalCaseClaimantDocument.setUploadedAt(LocalDateTime.now());

        legalCaseClaimantDocumentRepository.save(legalCaseClaimantDocument);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto createLegalCaseRespondantDocument(CreateLegalCaseRespondantDocumentRequestDto createLegalCaseRespondantDocumentRequestDto) {
        LegalCaseRespondantDocument legalCaseRespondantDocument = new LegalCaseRespondantDocument();

        legalCaseRespondantDocument.setRespondantId(createLegalCaseRespondantDocumentRequestDto.getRespondantId());
        legalCaseRespondantDocument.setLegalCaseId(createLegalCaseRespondantDocumentRequestDto.getLegalCaseId());
        legalCaseRespondantDocument.setRespondantName(createLegalCaseRespondantDocumentRequestDto.getRespondantName());
        legalCaseRespondantDocument.setDocumentTitle(createLegalCaseRespondantDocumentRequestDto.getDocumentTitle());
        legalCaseRespondantDocument.setDocumentUrl(createLegalCaseRespondantDocumentRequestDto.getDocumentUrl());
        legalCaseRespondantDocument.setUploadedAt(LocalDateTime.now());

        legalCaseRespondantDocumentRepository.save(legalCaseRespondantDocument);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto updateLegalCaseDocument(Long caseId, UpdateLegalCaseClaimantDocumentRequestDto updateLegalCaseClaimantDocumentRequestDto) {

        LegalCase legalCase = legalCaseRepository.findById(caseId).orElseThrow(()->new RuntimeException("legal case with id -> "+ caseId+" not found" ));

        legalCase.setSection17DocPath(updateLegalCaseClaimantDocumentRequestDto.getSection17DocPath());
        legalCase.setStatementOfClaimPath(updateLegalCaseClaimantDocumentRequestDto.getStatementOfClaimPath());
        legalCase.setAdditionalDocumentPath(updateLegalCaseClaimantDocumentRequestDto.getAdditionalDocumentPath());

        LegalCase legalCase1 = legalCaseRepository.save(legalCase);
        ArbitratorLegalCase arbitratorLegalCase = arbitratorLegalCaseRepository.findByLegalCaseId(legalCase1.getId());
        Arbitrator arbitrator = arbitratorRepository.findById(arbitratorLegalCase.getArbitratorId()).orElseThrow(()->new RuntimeException("arbitrator with id ->"+arbitratorLegalCase.getArbitratorId()+" not found"));

        CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
        String messageBody = MailTemplate.generateCaseDocumentReviewEmail(legalCase1.getId());
        createEmailRequestDto.setMsgBody(messageBody);
        createEmailRequestDto.setRecipient(arbitrator.getEmail());
        emailService.sendSystemMail(createEmailRequestDto);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public GenericResponseDto createLegalCaseLog(CreateLegalCaseLogRequestDto createLegalCaseLogRequestDto) {

        LegalCaseLog legalCaseLog = new LegalCaseLog();
        legalCaseLog.setCreatedAt(Instant.now());
        legalCaseLog.setLegalCaseId(createLegalCaseLogRequestDto.getLegalCaseId());
        legalCaseLog.setCreatedById(createLegalCaseLogRequestDto.getCreatedById());
        legalCaseLog.setCreatedByName(createLegalCaseLogRequestDto.getCreatedByName());
        legalCaseLog.setStatus(createLegalCaseLogRequestDto.getStatus());
        legalCaseLog.setArbitratorId(createLegalCaseLogRequestDto.getArbitratorId());
        legalCaseLog.setArbitratorName(createLegalCaseLogRequestDto.getArbitratorName());
        legalCaseLog.setRecordingLink(createLegalCaseLogRequestDto.getRecordingLink());
        legalCaseLog.setLastHearingDate(createLegalCaseLogRequestDto.getLastHearingDate());
        legalCaseLog.setNextHearingDate(createLegalCaseLogRequestDto.getNextHearingDate());
        legalCaseLog.setPurposeOfHearing(createLegalCaseLogRequestDto.getPurposeOfHearing());
        legalCaseLog.setAwardStatement(createLegalCaseLogRequestDto.getAwardStatement());

        LegalCaseLog legalCaseLog1 = legalCaseLogRepository.save(legalCaseLog);

        // 🔥 DELETE old schedule if exists
        legalCaseHearingScheduleRepository.deleteByLegalCaseId(legalCaseLog1.getLegalCaseId());

        LegalCaseHearingSchedule legalCaseHearingSchedule = new LegalCaseHearingSchedule();
        legalCaseHearingSchedule.setLastHearingDate(legalCaseLog1.getLastHearingDate());
        legalCaseHearingSchedule.setNextHearingDate(legalCaseLog1.getNextHearingDate());
        legalCaseHearingSchedule.setStatus(legalCaseLog1.getStatus());
        legalCaseHearingSchedule.setLegalCaseId(legalCaseLog1.getLegalCaseId());

        legalCaseHearingScheduleRepository.save(legalCaseHearingSchedule);


        if(legalCaseLog1.getStatus().equals(HearingStatus.CLOSED)){
            sendCaseClosedNotificationToAll(legalCaseLog1.getLegalCaseId(),legalCaseLog1.getArbitratorId());
        }

        return new GenericResponseDto("success","record added successfully");
    }


    private void sendCaseClosedNotificationToAll(Long legalCaseId,Long arbitratorId){
        Arbitrator arbitrator = arbitratorRepository.findById(arbitratorId).orElseThrow(()->new RuntimeException("Arbitrator with id not found -> "+arbitratorId));
        LegalCase legalCase = legalCaseRepository.findById(legalCaseId).orElseThrow(()->new RuntimeException("legal case with id not found -> "+legalCaseId));
        AdmissionForm form = admissionFormRepository.findById(legalCase.getAdmissionFormId()).orElseThrow(()->new RuntimeException("AdmissionForm with id not found -> "+legalCase.getAdmissionFormId()));
        List<AdmissionFormClaimant> claimants =
                admissionFormClaimantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

        List<AdmissionFormRespondant> respondants =
                admissionFormRespondantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

        String claimantNames = claimants.stream()
                .map(AdmissionFormClaimant::getFullName)
                .collect(Collectors.joining(", "));

        String respondantNames = respondants.stream()
                .map(AdmissionFormRespondant::getFullName)
                .collect(Collectors.joining(", "));

        String message = MailTemplate.generateCaseClosedEmail(
                form.getAdmissionFormNo(),
                arbitrator.getFullName(),
                arbitrator.getPhoneNumber(),
                claimantNames,
                respondantNames
        );
        CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();

        //        all claimant email
        claimants.forEach(obj->{
            createEmailRequestDto.setRecipient(obj.getEmail());
            createEmailRequestDto.setMsgBody(message);
            emailService.sendSystemMail(createEmailRequestDto);
        });

        //        all respondant email
        respondants.forEach(obj->{
            createEmailRequestDto.setRecipient(obj.getEmail());
            createEmailRequestDto.setMsgBody(message);
            emailService.sendSystemMail(createEmailRequestDto);
        });

        //        arbitrator email
        createEmailRequestDto.setRecipient(arbitrator.getEmail());
        createEmailRequestDto.setMsgBody(message);
        emailService.sendSystemMail(createEmailRequestDto);

        //        admin email
        createEmailRequestDto.setRecipient(emailService.getAdminReceiverEmail());
        createEmailRequestDto.setMsgBody(message);
        emailService.sendSystemMail(createEmailRequestDto);
    }


    @Override
    public GenericResponseDto checkIfLegalCaseIsApprovedByAdmin(Long caseId) {
        LegalCase legalCase = legalCaseRepository.findById(caseId).orElseThrow(()->new RuntimeException("Legal case with id -> "+ caseId+" not found"));
        if(!legalCase.getStatus().equals(CaseStatus.APPROVED)){
            return new GenericResponseDto("error", "Legal case is not yet approved by admin");
        }
        return new GenericResponseDto("success","Legal case is approved by admin");
    }

    @Override
    public GenericResponseDto updatedLegalCaseStatusByArbitrator(UpdateLegalCaseStatusRequestDto req) {

        LegalCase legalCase = legalCaseRepository.findById(req.getCaseId())
                .orElseThrow(() -> new RuntimeException("legal case with id -> " + req.getCaseId() + " not found"));

        if (req.getStatus().equals(CaseStatus.REJECTED)) {

            List<AdmissionFormClaimant> claimants =
                    admissionFormClaimantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

            String messageBody = MailTemplate.generateCaseDocumentRejectionEmail(
                    legalCase.getCaseNo(), req.getRejectionReason()
            );

            claimants.forEach(obj -> {
                CreateEmailRequestDto mail = new CreateEmailRequestDto();
                mail.setMsgBody(messageBody);
                mail.setRecipient(obj.getEmail());
                emailService.sendClaimantMail(mail);
            });

            legalCase.setStatus(CaseStatus.REJECTED);
        }

        if (req.getStatus().equals(CaseStatus.APPROVED)) {

            List<AdmissionFormClaimant> claimants =
                    admissionFormClaimantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

            List<AdmissionFormRespondant> respondants =
                    admissionFormRespondantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

            String messageBody = MailTemplate.generateCaseApprovedEmail(legalCase.getCaseNo());

            // Mail to Claimants
            claimants.forEach(obj -> {
                CreateEmailRequestDto mail = new CreateEmailRequestDto();
                mail.setMsgBody(messageBody);
                mail.setRecipient(obj.getEmail());
                emailService.sendClaimantMail(mail);
            });

            // Mail to Respondents
            respondants.forEach(obj -> {
                CreateEmailRequestDto mail = new CreateEmailRequestDto();
                mail.setMsgBody(messageBody);
                mail.setRecipient(obj.getEmail());
                emailService.sendRespondentMail(mail);
            });

            legalCase.setStatus(CaseStatus.APPROVED);
        }

        legalCase.setUpdatedAt(Instant.now());
        legalCaseRepository.save(legalCase);

        return new GenericResponseDto("success", "Legal case status updated and parties notified");
    }


    @Override
    public Page<LegalCaseMniResponseDto> getAllLegalCase(Pageable pageable) {
        return legalCaseRepository.findAll(pageable)
                .map(this::mapToMiniDto);
    }

    @Override
    public Page<LegalCaseMniResponseDto> getAllLegalCaseByUserType(Pageable pageable, UserCaseType userCaseType) {
        return legalCaseRepository.findAll(pageable)
                .map(legalCase -> mapToMiniDtoWithUserType(legalCase, userCaseType));
    }

    @Override
    public LegalCaseMniResponseDto getLegalCaseMniDetailByLegalCaseId(Long caseId) {
        return legalCaseRepository.findById(caseId)
                .map(this::mapToMiniDto)
                .orElseThrow(() -> new RuntimeException("Legal case with id -> " + caseId + " not found"));
    }


    private LegalCaseMniResponseDto mapToLegalCaseMiniResponseDto(LegalCase req){

        List<AdmissionFormClaimant> claimants =
                admissionFormClaimantRepository.findByAdmissionFormId(req.getAdmissionFormId());

        List<AdmissionFormRespondant> respondants =
                admissionFormRespondantRepository.findByAdmissionFormId(req.getAdmissionFormId());

        String claimantNames = claimants.stream()
                .map(AdmissionFormClaimant::getFullName)
                .collect(Collectors.joining(", "));

        String respondantNames = respondants.stream()
                .map(AdmissionFormRespondant::getFullName)
                .collect(Collectors.joining(", "));

        LegalCaseMniResponseDto res = new LegalCaseMniResponseDto();
        res.setId(req.getId());
        res.setCaseNo(req.getCaseNo());
        res.setSection17DocPath(req.getSection17DocPath());
        res.setStatementOfClaimPath(req.getStatementOfClaimPath());
        res.setAdditionalDocumentPath(req.getAdditionalDocumentPath());
        res.setStatus(req.getStatus());
        res.setClaimants(claimantNames);
        res.setRespondants(respondantNames);
        res.setCreatedAt(req.getCreatedAt());
        res.setCreatedByName(req.getCreatedByName());
        res.setUpdatedAt(req.getUpdatedAt());
        res.setUpdatedById(req.getUpdatedById());
        res.setUpdatedByName(req.getUpdatedByName());

        return res;
    }


    @Override
    public Page<LegalCaseMniResponseDto> getAllLegalCaseByUserIdAndUserType(PageRequest pageRequest, Long userId, UserCaseType userCaseType) {

        // Step 1: Get all admission mappings for this user and type
        List<AdmissionFormUser> admissionFormUsers =
                admissionFormUserRepository.findByUserIdAndUserCaseType(userId, userCaseType);

        if (admissionFormUsers.isEmpty()) {
            return Page.empty(pageRequest);
        }

        // Step 2: Extract admissionFormIds
        List<Long> admissionIds = admissionFormUsers.stream()
                .map(AdmissionFormUser::getAdmissionId)
                .distinct()
                .collect(Collectors.toList());

        // Step 3: Fetch legal cases by admissionFormIds with pagination
        Page<LegalCase> legalCasesPage =
                legalCaseRepository.findByAdmissionFormIdIn(admissionIds, pageRequest);

        // Step 4: Map to Mini DTO
        return legalCasesPage.map(this::mapToMiniDto);
    }


    @Override
    public LegalCaseResponseDto getLegalCaseDetailByLegalCaseId(Long caseId) {

        LegalCase legalCase = legalCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Legal case with id -> " + caseId + " not found"));

        List<LegalCaseLog> legalCaseLog =
                legalCaseLogRepository.findAllByLegalCaseId(caseId);

        List<LegalCaseClaimantDocument> legalCaseClaimantDocuments =
                legalCaseClaimantDocumentRepository.findAllByLegalCaseId(caseId);

        List<LegalCaseRespondantDocument> legalCaseRespondantDocuments =
                legalCaseRespondantDocumentRepository.findAllByLegalCaseId(caseId);

        Optional<LegalCaseHearingSchedule> legalCaseHearingScheduleOpt =
                legalCaseHearingScheduleRepository.findByLegalCaseId(caseId);

        ArbitratorUser arbitratorUser = null;
        User user = null;

        ArbitratorLegalCase arbitratorLegalCase =
                arbitratorLegalCaseRepository.findByLegalCaseId(caseId);

        if (arbitratorLegalCase != null) {
            arbitratorUser = arbitratorUserRepository
                    .findByArbitratorId(arbitratorLegalCase.getArbitratorId())
                    .orElse(null);

            if (arbitratorUser != null) {
                user = userRepository.findById(arbitratorUser.getUserId()).orElse(null);
            }
        }

        LegalCaseResponseDto legalCaseResponseDto = new LegalCaseResponseDto();
        legalCaseResponseDto.setCaseStatus(legalCase.getStatus());
        legalCaseResponseDto.setCaseNo(legalCase.getCaseNo());
        legalCaseResponseDto.setId(legalCase.getId());
        legalCaseResponseDto.setCreatedAt(legalCase.getCreatedAt());

        if (arbitratorUser != null) {
            legalCaseResponseDto.setArbitratorId(arbitratorUser.getArbitratorId());
        }

        if (user != null) {
            legalCaseResponseDto.setArbitratorName(user.getFullName());
        }

        legalCaseResponseDto.setLegalCaseClaimantDocumentResponseDtos(
                mapToLegalCaseClaimantDocumentResponseDto(legalCaseClaimantDocuments)
        );

        legalCaseResponseDto.setLegalCaseLogResponseDtos(
                mapToLegalCaseLogResponseDto(legalCaseLog)
        );

        legalCaseHearingScheduleOpt.ifPresent(schedule ->
                legalCaseResponseDto.setLegalCaseHearingScheduleResponseDto(
                        mapToLegalCaseHearingScheduleResponseDto(schedule))
        );

        legalCaseResponseDto.setLegalCaseRespondantDocumentResponseDtos(
                mapToLegalCaseRespondantDocumentResponseDto(legalCaseRespondantDocuments)
        );

        return legalCaseResponseDto;
    }

    @Override
    public Page<LegalCaseMniResponseDto> getAllLegalCaseByArbitratorId(PageRequest pageRequest, Long arbitratorId) {

        // Step 1: Get mapping rows
        Page<ArbitratorLegalCase> arbitratorCases =
                arbitratorLegalCaseRepository.findAllByArbitratorId(arbitratorId, pageRequest);

        // Step 2: Extract LegalCase IDs
        List<Long> caseIds = arbitratorCases.getContent().stream()
                .map(ArbitratorLegalCase::getLegalCaseId)
                .collect(Collectors.toList());

        if (caseIds.isEmpty()) {
            return Page.empty(pageRequest);
        }

        // Step 3: Fetch LegalCase entities
        Page<LegalCase> legalCases =
                legalCaseRepository.findByIdIn(caseIds, pageRequest);

        // Step 4: Map to Mini DTO
        return legalCases.map(this::mapToMiniDto);
    }


    private LegalCaseHearingScheduleResponseDto mapToLegalCaseHearingScheduleResponseDto(LegalCaseHearingSchedule obj) {
        if (obj == null) return null;
        LegalCaseHearingScheduleResponseDto res = new LegalCaseHearingScheduleResponseDto();
        res.setLegalCaseId(obj.getLegalCaseId());
        res.setStatus(obj.getStatus());
        res.setLastHearingDate(obj.getLastHearingDate());
        res.setNextHearingDate(obj.getNextHearingDate());
        res.setId(obj.getId());
        return res;
    }


    private List<LegalCaseClaimantDocumentResponseDto> mapToLegalCaseClaimantDocumentResponseDto(List<LegalCaseClaimantDocument> legalCaseClaimantDocuments){
        List<LegalCaseClaimantDocumentResponseDto> res = new ArrayList<>();
        legalCaseClaimantDocuments.forEach(obj->{
            LegalCaseClaimantDocumentResponseDto req = new LegalCaseClaimantDocumentResponseDto();
            req.setClaimantId(obj.getClaimantId());
            req.setLegalCaseId(obj.getLegalCaseId());
            req.setClaimantName(obj.getClaimantName());
            req.setId(obj.getId());
            req.setDocumentUrl(obj.getDocumentUrl());
            req.setDocumentTitle(obj.getDocumentTitle());
            req.setUploadedAt(obj.getUploadedAt());
            res.add(req);
        });
        return res;
    }

    private List<LegalCaseRespondantDocumentResponseDto> mapToLegalCaseRespondantDocumentResponseDto(List<LegalCaseRespondantDocument> legalCaseRespondantDocuments){
        List<LegalCaseRespondantDocumentResponseDto> res = new ArrayList<>();
        legalCaseRespondantDocuments.forEach(obj->{
            LegalCaseRespondantDocumentResponseDto req = new LegalCaseRespondantDocumentResponseDto();
            req.setRespondantId(obj.getRespondantId());
            req.setLegalCaseId(obj.getLegalCaseId());
            req.setRespondantName(obj.getRespondantName());
            req.setId(obj.getId());
            req.setDocumentUrl(obj.getDocumentUrl());
            req.setDocumentTitle(obj.getDocumentTitle());
            req.setUploadedAt(obj.getUploadedAt());
            res.add(req);
        });
        return res;
    }

    private List<LegalCaseLogResponseDto> mapToLegalCaseLogResponseDto(List<LegalCaseLog> legalCaseLogs){
        List<LegalCaseLogResponseDto> res = new ArrayList<>();
        legalCaseLogs.forEach(obj->{
            LegalCaseLogResponseDto req = new LegalCaseLogResponseDto();
            req.setArbitratorId(obj.getArbitratorId());
            req.setLegalCaseId(obj.getLegalCaseId());
            req.setArbitratorName(obj.getArbitratorName());
            req.setStatus(obj.getStatus());
            req.setRecordingLink(obj.getRecordingLink());
            req.setCreatedAt(obj.getCreatedAt());
            req.setLastHearingDate(obj.getLastHearingDate());
            req.setNextHearingDate(obj.getNextHearingDate());
            req.setPurposeOfHearing(obj.getPurposeOfHearing());
            req.setAwardStatement(obj.getAwardStatement());
            req.setCreatedById(obj.getCreatedById());
            req.setCreatedByName(obj.getCreatedByName());
            res.add(req);
        });
        return res;
    }

    private LegalCaseMniResponseDto mapToMiniDto(LegalCase legalCase) {

        List<AdmissionFormClaimant> claimants =
                admissionFormClaimantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

        List<AdmissionFormRespondant> respondants =
                admissionFormRespondantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

        LegalCaseHearingSchedule legalCaseHearingSchedule = legalCaseHearingScheduleRepository.findByLegalCaseId(legalCase.getId()).orElse(null);
        HearingStatus hearingStatus = HearingStatus.NOT_STARTED;

        if(legalCaseHearingSchedule != null) hearingStatus = legalCaseHearingSchedule.getStatus();

        String claimantNames = claimants.stream()
                .map(AdmissionFormClaimant::getFullName)
                .collect(Collectors.joining(", "));

        String respondantNames = respondants.stream()
                .map(AdmissionFormRespondant::getFullName)
                .collect(Collectors.joining(", "));

        return new LegalCaseMniResponseDto(
                legalCase.getId(),
                legalCase.getCaseNo(),
                legalCase.getSection17DocPath(),
                legalCase.getStatementOfClaimPath(),
                legalCase.getAdditionalDocumentPath(),
                legalCase.getStatus(),
                claimantNames,
                respondantNames,
                hearingStatus,
                legalCase.getCreatedAt(),
                legalCase.getCreatedByName(),
                legalCase.getUpdatedAt(),
                legalCase.getUpdatedById(),
                legalCase.getUpdatedByName()
        );
    }

    private LegalCaseMniResponseDto mapToMiniDtoWithUserType(LegalCase legalCase, UserCaseType userCaseType) {

        List<AdmissionFormUser> users =
                admissionFormUserRepository.findByAdmissionIdAndUserCaseType(
                        legalCase.getAdmissionFormId(), userCaseType);

        List<AdmissionFormClaimant> claimants =
                admissionFormClaimantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

        List<AdmissionFormRespondant> respondants =
                admissionFormRespondantRepository.findByAdmissionFormId(legalCase.getAdmissionFormId());

        LegalCaseHearingSchedule legalCaseHearingSchedule = legalCaseHearingScheduleRepository.findByLegalCaseId(legalCase.getId()).orElse(null);
        HearingStatus hearingStatus = HearingStatus.NOT_STARTED;

        if(legalCaseHearingSchedule != null) hearingStatus = legalCaseHearingSchedule.getStatus();

        String claimantNames = claimants.stream()
                .map(AdmissionFormClaimant::getFullName)
                .collect(Collectors.joining(", "));

        String respondantNames = respondants.stream()
                .map(AdmissionFormRespondant::getFullName)
                .collect(Collectors.joining(", "));

        return new LegalCaseMniResponseDto(
                legalCase.getId(),
                legalCase.getCaseNo(),
                legalCase.getSection17DocPath(),
                legalCase.getStatementOfClaimPath(),
                legalCase.getAdditionalDocumentPath(),
                legalCase.getStatus(),
                claimantNames,
                respondantNames,
                hearingStatus,
                legalCase.getCreatedAt(),
                legalCase.getCreatedByName(),
                legalCase.getUpdatedAt(),
                legalCase.getUpdatedById(),
                legalCase.getUpdatedByName()
        );
    }


}
