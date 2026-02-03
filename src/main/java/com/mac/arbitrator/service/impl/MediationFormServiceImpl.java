package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.dto.request.update.UpdateMediationFormCaseDetailRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateMediationFormStatus;
import com.mac.arbitrator.dto.response.MediationResponseDto;
import com.mac.arbitrator.dto.response.ClaimantResponseDto;
import com.mac.arbitrator.dto.response.DocumentsResponseDto;
import com.mac.arbitrator.dto.response.RespondantResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.FormStatus;
import com.mac.arbitrator.exception.ResourceNotFoundException;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.MediationFormService;
import com.mac.arbitrator.util.MailTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MediationFormServiceImpl implements MediationFormService {

    private final MediationFormRepository mediaitonFormRepository;
    private final MediationFormDocumentsRepository mediaitonFormDocumentsRepository;
    private final MediationFormClaimantRepository claimantRepository;
    private final MediationFormRespondantRepository respondantRepository;
    private final EmailService emailService;
    private final MediationFormCaseDetailRepository mediationFormCaseDetailRepository;
    private final LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository;

    public MediationFormServiceImpl(MediationFormRepository mediaitonFormRepository, MediationFormDocumentsRepository mediaitonFormDocumentsRepository, MediationFormClaimantRepository claimantRepository, MediationFormRespondantRepository respondantRepository, EmailService emailService, MediationFormCaseDetailRepository mediationFormCaseDetailRepository, LegalCaseHearingScheduleRepository legalCaseHearingScheduleRepository) {
        this.mediaitonFormRepository = mediaitonFormRepository;
        this.mediaitonFormDocumentsRepository = mediaitonFormDocumentsRepository;
        this.claimantRepository = claimantRepository;
        this.respondantRepository = respondantRepository;
        this.emailService = emailService;
        this.mediationFormCaseDetailRepository = mediationFormCaseDetailRepository;
        this.legalCaseHearingScheduleRepository = legalCaseHearingScheduleRepository;
    }

    @Override
    public Page<MediationResponseDto> getAllMediations(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<MediationForm> mediationFormsPage =
                mediaitonFormRepository.findAll(pageable);

        return mediationFormsPage.map(mediaitonForm -> {

            MediationResponseDto dto = new MediationResponseDto();
            dto.setId(mediaitonForm.getId());
            dto.setMediationFromNo(mediaitonForm.getMediationFormNo());
            dto.setArbitrationClause(mediaitonForm.getArbitrationClause());
            dto.setDisputeAmount(mediaitonForm.getDisputeAmount());
            dto.setDisputeDate(mediaitonForm.getDisputeDate());
            dto.setDefaultClause(mediaitonForm.getDefaultClause());
            dto.setJurisdictionId(mediaitonForm.getJurisdictionId());
            dto.setJurisdictionName(mediaitonForm.getJurisdictionName());
            dto.setRefiefSought(mediaitonForm.getReliefSought());
            dto.setStatus(mediaitonForm.getStatus().name());

            /* -------------------- CLAIMANTS -------------------- */
            List<ClaimantResponseDto> claimantDtos =
                    claimantRepository.findByMediationFormId(mediaitonForm.getId())
                            .stream()
                            .map(claimant -> {
                                ClaimantResponseDto c = new ClaimantResponseDto();
                                c.setId(claimant.getId());
                                c.setFullName(claimant.getFullName());
                                c.setPartyTypeId(claimant.getPartyTypeId());
                                c.setPartyTypeName(claimant.getPartyTypeName());
                                c.setEmail(claimant.getEmail());
                                c.setSecondaryEmail(claimant.getSecondaryEmail());
                                c.setPhoneNumber(claimant.getPhoneNumber());

                                c.setCountryId(claimant.getCountryId());
                                c.setCountryName(claimant.getCountryName());
                                c.setStateId(claimant.getStateId());
                                c.setStateName(claimant.getStateName());
                                c.setCityId(claimant.getCityId());
                                c.setCityName(claimant.getCityName());
                                c.setZipCode(claimant.getZipCode());
                                c.setAddress(claimant.getAddress());

                                c.setSecondaryCountryId(claimant.getSecondaryCountryId());
                                c.setSecondaryCountryName(claimant.getSecondaryCountryName());
                                c.setSecondaryStateId(claimant.getSecondaryStateId());
                                c.setSecondaryStateName(claimant.getSecondaryStateName());
                                c.setSecondaryCityId(claimant.getSecondaryCityId());
                                c.setSecondaryCityName(claimant.getSecondaryCityName());
                                c.setSecondaryZipCode(claimant.getSecondaryZipCode());
                                c.setSecondaryAddress(claimant.getSecondaryAddress());
                                return c;
                            }).toList();

            dto.setClaimants(claimantDtos);

            /* -------------------- RESPONDANTS -------------------- */
            List<RespondantResponseDto> respondantDtos =
                    respondantRepository.findByMediationFormId(mediaitonForm.getId())
                            .stream()
                            .map(respondant -> {
                                RespondantResponseDto r = new RespondantResponseDto();
                                r.setId(respondant.getId());
                                r.setFullName(respondant.getFullName());
                                r.setEmail(respondant.getEmail());
                                r.setSecondaryEmail(respondant.getSecondaryEmail());
                                r.setPhoneNumber(respondant.getPhoneNumber());

                                r.setCountryId(respondant.getCountryId());
                                r.setCountryName(respondant.getCountryName());
                                r.setStateId(respondant.getStateId());
                                r.setStateName(respondant.getStateName());
                                r.setCityId(respondant.getCityId());
                                r.setCityName(respondant.getCityName());
                                r.setZipCode(respondant.getZipCode());
                                r.setAddress(respondant.getAddress());

                                r.setSecondaryCountryId(respondant.getSecondaryCountryId());
                                r.setSecondaryCountryName(respondant.getSecondaryCountryName());
                                r.setSecondaryStateId(respondant.getSecondaryStateId());
                                r.setSecondaryStateName(respondant.getSecondaryStateName());
                                r.setSecondaryCityId(respondant.getSecondaryCityId());
                                r.setSecondaryCityName(respondant.getSecondaryCityName());
                                r.setSecondaryZipCode(respondant.getSecondaryZipCode());
                                r.setSecondaryAddress(respondant.getSecondaryAddress());
                                return r;
                            }).toList();

            dto.setRespondants(respondantDtos);

            /* -------------------- DOCUMENTS -------------------- */
            MediationFormDocuments documents =
                    mediaitonFormDocumentsRepository
                            .findByMediationFormId(mediaitonForm.getId());

            if (documents != null) {
                DocumentsResponseDto docDto = new DocumentsResponseDto();
                docDto.setId(documents.getId());
                docDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                docDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                docDto.setAgreementContract(documents.getAgreementContract());
                dto.setDocuments(docDto);
            }

            return dto;
        });
    }


    @Override
    public List<MediationResponseDto> getAllMediations() {

        List<MediationForm> mediaitonForms = mediaitonFormRepository.findAll();

        return mediaitonForms.stream().map(mediaitonForm -> {

            MediationResponseDto dto = new MediationResponseDto();
            dto.setId(mediaitonForm.getId());
            dto.setMediationFromNo(mediaitonForm.getMediationFormNo());
            dto.setArbitrationClause(mediaitonForm.getArbitrationClause());
            dto.setDisputeAmount(mediaitonForm.getDisputeAmount());
            dto.setDisputeDate(mediaitonForm.getDisputeDate());
            dto.setDefaultClause(mediaitonForm.getDefaultClause());
            dto.setJurisdictionId(mediaitonForm.getJurisdictionId());
            dto.setJurisdictionName(mediaitonForm.getJurisdictionName());
            dto.setRefiefSought(mediaitonForm.getReliefSought());
            dto.setStatus(mediaitonForm.getStatus().name());

            /* -------------------- CLAIMANTS -------------------- */
            List<ClaimantResponseDto> claimantDtos =
                    claimantRepository.findByMediationFormId(mediaitonForm.getId())
                            .stream()
                            .map(claimant -> {
                                ClaimantResponseDto c = new ClaimantResponseDto();
                                c.setId(claimant.getId());
                                c.setFullName(claimant.getFullName());
                                c.setPartyTypeId(claimant.getPartyTypeId());
                                c.setPartyTypeName(claimant.getPartyTypeName());
                                c.setEmail(claimant.getEmail());
                                c.setSecondaryEmail(claimant.getSecondaryEmail());
                                c.setPhoneNumber(claimant.getPhoneNumber());

                                c.setCountryId(claimant.getCountryId());
                                c.setCountryName(claimant.getCountryName());
                                c.setStateId(claimant.getStateId());
                                c.setStateName(claimant.getStateName());
                                c.setCityId(claimant.getCityId());
                                c.setCityName(claimant.getCityName());
                                c.setZipCode(claimant.getZipCode());
                                c.setAddress(claimant.getAddress());

                                c.setSecondaryCountryId(claimant.getSecondaryCountryId());
                                c.setSecondaryCountryName(claimant.getSecondaryCountryName());
                                c.setSecondaryStateId(claimant.getSecondaryStateId());
                                c.setSecondaryStateName(claimant.getSecondaryStateName());
                                c.setSecondaryCityId(claimant.getSecondaryCityId());
                                c.setSecondaryCityName(claimant.getSecondaryCityName());
                                c.setSecondaryZipCode(claimant.getSecondaryZipCode());
                                c.setSecondaryAddress(claimant.getSecondaryAddress());

                                return c;
                            }).toList();

            dto.setClaimants(claimantDtos);

            /* -------------------- RESPONDANTS -------------------- */
            List<RespondantResponseDto> respondantDtos =
                    respondantRepository.findByMediationFormId(mediaitonForm.getId())
                            .stream()
                            .map(respondant -> {
                                RespondantResponseDto r = new RespondantResponseDto();
                                r.setId(respondant.getId());
                                r.setFullName(respondant.getFullName());
                                r.setEmail(respondant.getEmail());
                                r.setSecondaryEmail(respondant.getSecondaryEmail());
                                r.setPhoneNumber(respondant.getPhoneNumber());

                                r.setCountryId(respondant.getCountryId());
                                r.setCountryName(respondant.getCountryName());
                                r.setStateId(respondant.getStateId());
                                r.setStateName(respondant.getStateName());
                                r.setCityId(respondant.getCityId());
                                r.setCityName(respondant.getCityName());
                                r.setZipCode(respondant.getZipCode());
                                r.setAddress(respondant.getAddress());

                                r.setSecondaryCountryId(respondant.getSecondaryCountryId());
                                r.setSecondaryCountryName(respondant.getSecondaryCountryName());
                                r.setSecondaryStateId(respondant.getSecondaryStateId());
                                r.setSecondaryStateName(respondant.getSecondaryStateName());
                                r.setSecondaryCityId(respondant.getSecondaryCityId());
                                r.setSecondaryCityName(respondant.getSecondaryCityName());
                                r.setSecondaryZipCode(respondant.getSecondaryZipCode());
                                r.setSecondaryAddress(respondant.getSecondaryAddress());

                                return r;
                            }).toList();

            dto.setRespondants(respondantDtos);

            /* -------------------- DOCUMENTS -------------------- */
            MediationFormDocuments documents =
                    mediaitonFormDocumentsRepository.findByMediationFormId(mediaitonForm.getId());

            if (documents != null) {
                DocumentsResponseDto docDto = new DocumentsResponseDto();
                docDto.setId(documents.getId());
                docDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                docDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                docDto.setAgreementContract(documents.getAgreementContract());
                dto.setDocuments(docDto);
            }

            return dto;

        }).toList();
    }

    @Override
    public MediationResponseDto getMediationById(Long id) {

        MediationForm mediaitonForm = mediaitonFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "MediationForm",
                        "id",
                        id
                ));

        MediationResponseDto dto = new MediationResponseDto();
        dto.setId(mediaitonForm.getId());
        dto.setArbitrationClause(mediaitonForm.getArbitrationClause());
        dto.setDisputeAmount(mediaitonForm.getDisputeAmount());
        dto.setDisputeDate(mediaitonForm.getDisputeDate());
        dto.setDefaultClause(mediaitonForm.getDefaultClause());
        dto.setJurisdictionId(mediaitonForm.getJurisdictionId());
        dto.setJurisdictionName(mediaitonForm.getJurisdictionName());
        dto.setRefiefSought(mediaitonForm.getReliefSought());
        dto.setStatus(mediaitonForm.getStatus().name());
        dto.setCreatedAt(mediaitonForm.getCreatedAt());

        /* -------------------- CLAIMANTS -------------------- */
        List<ClaimantResponseDto> claimantDtos =
                claimantRepository.findByMediationFormId(mediaitonForm.getId())
                        .stream()
                        .map(claimant -> {
                            ClaimantResponseDto c = new ClaimantResponseDto();
                            c.setId(claimant.getId());
                            c.setFullName(claimant.getFullName());
                            c.setPartyTypeId(claimant.getPartyTypeId());
                            c.setPartyTypeName(claimant.getPartyTypeName());
                            c.setEmail(claimant.getEmail());
                            c.setSecondaryEmail(claimant.getSecondaryEmail());
                            c.setPhoneNumber(claimant.getPhoneNumber());

                            c.setCountryId(claimant.getCountryId());
                            c.setCountryName(claimant.getCountryName());
                            c.setStateId(claimant.getStateId());
                            c.setStateName(claimant.getStateName());
                            c.setCityId(claimant.getCityId());
                            c.setCityName(claimant.getCityName());
                            c.setZipCode(claimant.getZipCode());
                            c.setAddress(claimant.getAddress());

                            c.setSecondaryCountryId(claimant.getSecondaryCountryId());
                            c.setSecondaryCountryName(claimant.getSecondaryCountryName());
                            c.setSecondaryStateId(claimant.getSecondaryStateId());
                            c.setSecondaryStateName(claimant.getSecondaryStateName());
                            c.setSecondaryCityId(claimant.getSecondaryCityId());
                            c.setSecondaryCityName(claimant.getSecondaryCityName());
                            c.setSecondaryZipCode(claimant.getSecondaryZipCode());
                            c.setSecondaryAddress(claimant.getSecondaryAddress());

                            return c;
                        }).toList();

        dto.setClaimants(claimantDtos);

        /* -------------------- RESPONDANTS -------------------- */
        List<RespondantResponseDto> respondantDtos =
                respondantRepository.findByMediationFormId(mediaitonForm.getId())
                        .stream()
                        .map(respondant -> {
                            RespondantResponseDto r = new RespondantResponseDto();
                            r.setId(respondant.getId());
                            r.setFullName(respondant.getFullName());
                            r.setEmail(respondant.getEmail());
                            r.setSecondaryEmail(respondant.getSecondaryEmail());
                            r.setPhoneNumber(respondant.getPhoneNumber());

                            r.setCountryId(respondant.getCountryId());
                            r.setCountryName(respondant.getCountryName());
                            r.setStateId(respondant.getStateId());
                            r.setStateName(respondant.getStateName());
                            r.setCityId(respondant.getCityId());
                            r.setCityName(respondant.getCityName());
                            r.setZipCode(respondant.getZipCode());
                            r.setAddress(respondant.getAddress());

                            r.setSecondaryCountryId(respondant.getSecondaryCountryId());
                            r.setSecondaryCountryName(respondant.getSecondaryCountryName());
                            r.setSecondaryStateId(respondant.getSecondaryStateId());
                            r.setSecondaryStateName(respondant.getSecondaryStateName());
                            r.setSecondaryCityId(respondant.getSecondaryCityId());
                            r.setSecondaryCityName(respondant.getSecondaryCityName());
                            r.setSecondaryZipCode(respondant.getSecondaryZipCode());
                            r.setSecondaryAddress(respondant.getSecondaryAddress());

                            return r;
                        }).toList();

        dto.setRespondants(respondantDtos);

        /* -------------------- DOCUMENTS -------------------- */
        MediationFormDocuments documents =
                mediaitonFormDocumentsRepository.findByMediationFormId(mediaitonForm.getId());

        if (documents != null) {
            DocumentsResponseDto docDto = new DocumentsResponseDto();
            docDto.setId(documents.getId());
            docDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
            docDto.setLrnDemandNotice(documents.getLrnDemandNotice());
            docDto.setAgreementContract(documents.getAgreementContract());
            dto.setDocuments(docDto);
        }

        return dto;
    }


    @Override
    public GenericResponseDto createMediation(CreateMediationRequestDto createMediationRequestDto) {

        MediationForm mediaitonForm = mapToMediationFormEntity(createMediationRequestDto);

        MediationForm savedMediationForm = mediaitonFormRepository.saveAndFlush(mediaitonForm);

        createMediationRequestDto.getClaimants().forEach(claimantRequestDto -> {
            MediationFormClaimant claimant = mapToClaimantEntity(savedMediationForm.getId(),claimantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            claimantRepository.saveAndFlush(claimant);
        });

        createMediationRequestDto.getRespondants().forEach(respondantRequestDto -> {
            MediationFormRespondant respondant = mapToRespondantEntity(savedMediationForm.getId(),respondantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            respondantRepository.saveAndFlush(respondant);
        });

        CreateMediationDocumentsRequestDto documentsRequestDto = createMediationRequestDto.getDocuments();
        MediationFormDocuments mediaitonFormDocuments = mapToMediationFormDocumentsEntity(savedMediationForm.getId(),documentsRequestDto);
        mediaitonFormDocumentsRepository.saveAndFlush(mediaitonFormDocuments);

        String adminEmail = emailService.getAdminReceiverEmail();
        CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
        String messageBody = MailTemplate.generateMediationAdminEmail(savedMediationForm.getId());
        createEmailRequestDto.setMsgBody(messageBody);
        createEmailRequestDto.setRecipient(adminEmail);
        emailService.sendAdminMail(createEmailRequestDto);

        return new GenericResponseDto("success","Mediation Created Successfully");
    }

    @Override
    public GenericResponseDto deleteMediation(Long id) {

        if(mediaitonFormRepository.findById(id).isPresent()){
            mediaitonFormRepository.deleteById(id);
            return new GenericResponseDto("success","Mediation Deleted");
        }
        throw new ResourceNotFoundException("Mediation","Id",id);
    }

    @Override
    public GenericResponseDto createMediationCaseDetail(
            CreateMediationFormCaseDetailRequestDto createMediationFormCaseDetailRequestDto) {

        System.out.println("=== createMediationCaseDetail START ===");

        System.out.println("Incoming MediationFormId = "
                + createMediationFormCaseDetailRequestDto.getMediationFormId());

        MediationFormCaseDetail mediationFormCaseDetail = new MediationFormCaseDetail();

        mediationFormCaseDetail.setMediationFormId(
                createMediationFormCaseDetailRequestDto.getMediationFormId());
        mediationFormCaseDetail.setDateOfHearing(
                createMediationFormCaseDetailRequestDto.getDateOfHearing());
        mediationFormCaseDetail.setZoomLink(
                createMediationFormCaseDetailRequestDto.getZoomLink());

        System.out.println("Saving MediationFormCaseDetail...");
        MediationFormCaseDetail mediationFormCaseDetail1 =
                mediationFormCaseDetailRepository.save(mediationFormCaseDetail);

        System.out.println("Saved CaseDetail with ID = "
                + mediationFormCaseDetail1.getId());

        System.out.println("Fetching CLAIMANTS...");
        List<MediationFormClaimant> mediationFormClaimants =
                claimantRepository.findByMediationFormId(
                        createMediationFormCaseDetailRequestDto.getMediationFormId());

        System.out.println("Claimants count = " + mediationFormClaimants.size());

        System.out.println("Fetching RESPONDENTS...");
        List<MediationFormRespondant> mediationFormRespondants =
                respondantRepository.findByMediationFormId(
                        createMediationFormCaseDetailRequestDto.getMediationFormId());

        System.out.println("Respondents count = " + mediationFormRespondants.size());

        mediationFormClaimants.forEach(obj -> {
            System.out.println("Sending mail to CLAIMANT email = " + obj.getEmail());

            String messageBody =
                    MailTemplate.generateMediationFromCaseDetailClaimantEmail(
                            mediationFormCaseDetail1.getDateOfHearing(),
                            mediationFormCaseDetail1.getZoomLink());

            System.out.println("Generated claimant email body");

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(obj.getEmail());

            System.out.println("Calling sendClaimantMail...");
            emailService.sendClaimantMail(createEmailRequestDto);
            System.out.println("✅ Claimant mail method executed");
        });

        mediationFormRespondants.forEach(obj -> {
            System.out.println("Sending mail to RESPONDENT email = " + obj.getEmail());

            String messageBody =
                    MailTemplate.generateMediationFromCaseDetailRespondantEmail(
                            mediationFormCaseDetail1.getDateOfHearing(),
                            mediationFormCaseDetail1.getZoomLink());

            System.out.println("Generated respondent email body");

            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(obj.getEmail());

            System.out.println("Calling sendRespondentMail...");
            emailService.sendRespondentMail(createEmailRequestDto);
            System.out.println("✅ Respondent mail method executed");
        });

        System.out.println("=== createMediationCaseDetail END ===");

        return new GenericResponseDto("success",
                "Mediation Case Detail Created Successfully");
    }


    @Override
    public GenericResponseDto updateMediationCaseDetail(UpdateMediationFormCaseDetailRequestDto updateMediationFormCaseDetailRequestDto) {
        MediationFormCaseDetail mediationFormCaseDetail = mediationFormCaseDetailRepository.findById(updateMediationFormCaseDetailRequestDto.getMediationFormId()).orElseThrow(()-> new RuntimeException("Mediation with id -> "+updateMediationFormCaseDetailRequestDto.getMediationFormId()+" not found"));
        mediationFormCaseDetail.setRecordingLink(updateMediationFormCaseDetailRequestDto.getRecordingLink());

        MediationFormCaseDetail mediationFormCaseDetail1 = mediationFormCaseDetailRepository.save(mediationFormCaseDetail);

        List<MediationFormClaimant> mediationFormClaimants = claimantRepository.findByMediationFormId(updateMediationFormCaseDetailRequestDto.getMediationFormId());
        List<MediationFormRespondant> mediationFormRespondants = respondantRepository.findByMediationFormId(updateMediationFormCaseDetailRequestDto.getMediationFormId());

        mediationFormClaimants.forEach(obj->{
            String messageBody = MailTemplate.generateMediationFromCaseDetailClaimantRecordingEmail(mediationFormCaseDetail1.getDateOfHearing(),mediationFormCaseDetail1.getRecordingLink());
            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(obj.getEmail());
            emailService.sendClaimantMail(createEmailRequestDto);
        });

        mediationFormRespondants.forEach(obj->{
            String messageBody = MailTemplate.generateMediationFromCaseDetailRespondantRecordingEmail(mediationFormCaseDetail1.getDateOfHearing(),mediationFormCaseDetail1.getRecordingLink());
            CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
            createEmailRequestDto.setMsgBody(messageBody);
            createEmailRequestDto.setRecipient(obj.getEmail());
            emailService.sendRespondentMail(createEmailRequestDto);
        });

        return new GenericResponseDto("success","Mediation Case Detail Updated Successfully");
    }


    @Override
    public GenericResponseDto checkIfMeetLinkAlreadyExist(Long mediationId) {

        MediationFormCaseDetail mediationFormCaseDetail =
                mediationFormCaseDetailRepository.findByMediationFormId(mediationId);

        if (mediationFormCaseDetail == null) {
            return new GenericResponseDto("error", "Mediation case not found");
        }

        if (mediationFormCaseDetail.getZoomLink() != null && !mediationFormCaseDetail.getZoomLink().isEmpty()) {
            return new GenericResponseDto("success", "Meeting link already exists");
        }

        return new GenericResponseDto("error", "Meeting link not generated yet");
    }


    private MediationForm mapToMediationFormEntity(CreateMediationRequestDto createMediationRequestDto) {
        MediationForm dto = new MediationForm();
        dto.setArbitrationClause(createMediationRequestDto.getArbitrationClause());
        dto.setDisputeAmount(createMediationRequestDto.getDisputeAmount());
        dto.setDisputeDate(createMediationRequestDto.getDisputeDate());
        dto.setDefaultClause(createMediationRequestDto.getDefaultClause());
        dto.setJurisdictionId(createMediationRequestDto.getJurisdictionId());
        dto.setJurisdictionName(createMediationRequestDto.getJurisdictionName());
        dto.setReliefSought(createMediationRequestDto.getRefiefSought());
        dto.setStatus(FormStatus.valueOf(createMediationRequestDto.getStatus()));
        dto.setCreatedAt(LocalDate.now());
        return dto;
    }

    private MediationFormClaimant mapToClaimantEntity(Long mediaitonFormId, CreateMediationClaimantRequestDto claimantDto) {
        MediationFormClaimant claimant = new MediationFormClaimant();
        claimant.setMediationFormId(mediaitonFormId);
        claimant.setFullName(claimantDto.getFullName());
        claimant.setPartyTypeId(claimantDto.getPartyTypeId());
        claimant.setPartyTypeName(claimantDto.getPartyTypeName());
        claimant.setPhoneNumber(claimantDto.getPhoneNumber());
        claimant.setEmail(claimantDto.getEmail());
        claimant.setCountryId(claimantDto.getCountryId());
        claimant.setCountryName(claimantDto.getCountryName());
        claimant.setStateId(claimantDto.getStateId());
        claimant.setStateName(claimantDto.getStateName());
        claimant.setCityId(claimantDto.getCityId());
        claimant.setCityName(claimantDto.getCityName());
        claimant.setZipCode(claimantDto.getZipCode());
        claimant.setAddress(claimantDto.getAddress());
        claimant.setSecondaryEmail(claimantDto.getSecondaryEmail());
        claimant.setSecondaryCountryId(claimantDto.getSecondaryCountryId());
        claimant.setSecondaryCountryName(claimantDto.getSecondaryCountryName());
        claimant.setSecondaryStateId(claimantDto.getSecondaryStateId());
        claimant.setSecondaryStateName(claimantDto.getSecondaryStateName());
        claimant.setSecondaryCityId(claimantDto.getSecondaryCityId());
        claimant.setSecondaryCityName(claimantDto.getSecondaryCityName());
        claimant.setSecondaryZipCode(claimantDto.getSecondaryZipCode());
        claimant.setSecondaryAddress(claimantDto.getSecondaryAddress());
        return claimant;
    }

    private MediationFormRespondant mapToRespondantEntity(Long mediaitonFormId, CreateMediationRespondantRequestDto respondantDto) {
        MediationFormRespondant respondant = new MediationFormRespondant();
        respondant.setMediationFormId(mediaitonFormId);
        respondant.setFullName(respondantDto.getFullName());
        respondant.setPartyTypeId(respondantDto.getPartyTypeId());
        respondant.setPartyTypeName(respondantDto.getPartyTypeName());
        respondant.setPhoneNumber(respondantDto.getPhoneNumber());
        respondant.setEmail(respondantDto.getEmail());
        respondant.setCountryId(respondantDto.getCountryId());
        respondant.setCountryName(respondantDto.getCountryName());
        respondant.setStateId(respondantDto.getStateId());
        respondant.setStateName(respondantDto.getStateName());
        respondant.setCityId(respondantDto.getCityId());
        respondant.setCityName(respondantDto.getCityName());
        respondant.setZipCode(respondantDto.getZipCode());
        respondant.setAddress(respondantDto.getAddress());
        respondant.setSecondaryEmail(respondantDto.getSecondaryEmail());
        respondant.setSecondaryCountryId(respondantDto.getSecondaryCountryId());
        respondant.setSecondaryCountryName(respondantDto.getSecondaryCountryName());
        respondant.setSecondaryStateId(respondantDto.getSecondaryStateId());
        respondant.setSecondaryStateName(respondantDto.getSecondaryStateName());
        respondant.setSecondaryCityId(respondantDto.getSecondaryCityId());
        respondant.setSecondaryCityName(respondantDto.getSecondaryCityName());
        respondant.setSecondaryZipCode(respondantDto.getSecondaryZipCode());
        respondant.setSecondaryAddress(respondantDto.getSecondaryAddress());
        return respondant;
    }

    private MediationFormDocuments mapToMediationFormDocumentsEntity(Long mediaitonFormId, CreateMediationDocumentsRequestDto documentsRequestDto) {
        MediationFormDocuments mediaitonFormDocuments = new MediationFormDocuments();
        mediaitonFormDocuments.setMediationFormId(mediaitonFormId);
        mediaitonFormDocuments.setPoaLoaIdCard(documentsRequestDto.getPoaLoaIdCard());
        mediaitonFormDocuments.setLrnDemandNotice(documentsRequestDto.getLrnDemandNotice());
        mediaitonFormDocuments.setAgreementContract(documentsRequestDto.getAgreementContract());
        return mediaitonFormDocuments;
    }

}
