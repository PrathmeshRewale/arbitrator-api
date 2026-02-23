package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.*;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.dto.response.ClaimantResponseDto;
import com.mac.arbitrator.dto.response.DocumentsResponseDto;
import com.mac.arbitrator.dto.response.RespondantResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.FormStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.exception.ResourceNotFoundException;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.AdmissionFormService;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.UserService;
import com.mac.arbitrator.util.MailTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdmissionFormServiceImpl implements AdmissionFormService {

    private AdmissionFormRepository admissionFormRepository;
    private AdmissionFormDocumentsRepository admissionFormDocumentsRepository;
    private AdmissionFormClaimantRepository claimantRepository;
    private AdmissionFormRespondantRepository respondantRepository;
    private final EmailService emailService;
    private final AdmissionFormUserRepository admissionFormUserRepository;
    private final UserService userService;

    public AdmissionFormServiceImpl(EmailService emailService, AdmissionFormUserRepository admissionFormUserRepository, UserService userService, AdmissionFormRespondantRepository respondantRepository, AdmissionFormClaimantRepository claimantRepository, AdmissionFormDocumentsRepository admissionFormDocumentsRepository, AdmissionFormRepository admissionFormRepository) {
        this.emailService = emailService;
        this.admissionFormUserRepository = admissionFormUserRepository;
        this.userService = userService;
        this.respondantRepository = respondantRepository;
        this.claimantRepository = claimantRepository;
        this.admissionFormDocumentsRepository = admissionFormDocumentsRepository;
        this.admissionFormRepository = admissionFormRepository;
    }

    @Override
    public Page<AdmissionResponseDto> getAllAdmissions(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AdmissionForm> admissionFormsPage =
                admissionFormRepository.findAll(pageable);

        return admissionFormsPage.map(admissionForm -> {

            AdmissionResponseDto dto = new AdmissionResponseDto();
            dto.setId(admissionForm.getId());
            dto.setAdmissionFromNo(admissionForm.getAdmissionFormNo());
            dto.setArbitrationClause(admissionForm.getArbitrationClause());
            dto.setDisputeAmount(admissionForm.getDisputeAmount());
            dto.setDisputeDate(admissionForm.getDisputeDate());
            dto.setDefaultClause(admissionForm.getDefaultClause());
            dto.setJurisdictionId(admissionForm.getJurisdictionId());
            dto.setJurisdictionName(admissionForm.getJurisdictionName());
            dto.setRefiefSought(admissionForm.getReliefSought());
            dto.setStatus(admissionForm.getStatus().name());

            /* -------------------- CLAIMANTS -------------------- */
            List<ClaimantResponseDto> claimantDtos =
                    claimantRepository.findByAdmissionFormId(admissionForm.getId())
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
                    respondantRepository.findByAdmissionFormId(admissionForm.getId())
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
            AdmissionFormDocuments documents =
                    admissionFormDocumentsRepository
                            .findByAdmissionFormId(admissionForm.getId());

            if (documents != null) {
                DocumentsResponseDto docDto = new DocumentsResponseDto();
                docDto.setId(documents.getId());
                docDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                docDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                docDto.setAgreementContract(documents.getAgreementContract());
                docDto.setOrders(documents.getOrders());
                dto.setDocuments(docDto);
            }

            return dto;
        });
    }


    @Override
    public List<AdmissionResponseDto> getAllAdmissions() {

        List<AdmissionForm> admissionForms = admissionFormRepository.findAll();

        return admissionForms.stream().map(admissionForm -> {

            AdmissionResponseDto dto = new AdmissionResponseDto();
            dto.setId(admissionForm.getId());
            dto.setAdmissionFromNo(admissionForm.getAdmissionFormNo());
            dto.setArbitrationClause(admissionForm.getArbitrationClause());
            dto.setDisputeAmount(admissionForm.getDisputeAmount());
            dto.setDisputeDate(admissionForm.getDisputeDate());
            dto.setDefaultClause(admissionForm.getDefaultClause());
            dto.setJurisdictionId(admissionForm.getJurisdictionId());
            dto.setJurisdictionName(admissionForm.getJurisdictionName());
            dto.setRefiefSought(admissionForm.getReliefSought());
            dto.setStatus(admissionForm.getStatus().name());

            /* -------------------- CLAIMANTS -------------------- */
            List<ClaimantResponseDto> claimantDtos =
                    claimantRepository.findByAdmissionFormId(admissionForm.getId())
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
                    respondantRepository.findByAdmissionFormId(admissionForm.getId())
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
            AdmissionFormDocuments documents =
                    admissionFormDocumentsRepository.findByAdmissionFormId(admissionForm.getId());

            if (documents != null) {
                DocumentsResponseDto docDto = new DocumentsResponseDto();
                docDto.setId(documents.getId());
                docDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                docDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                docDto.setAgreementContract(documents.getAgreementContract());
                docDto.setOrders(documents.getOrders());
                dto.setDocuments(docDto);
            }

            return dto;

        }).toList();
    }

    @Override
    public AdmissionResponseDto getAdmissionById(Long id) {

        AdmissionForm admissionForm = admissionFormRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "AdmissionForm",
                        "id",
                        id
                ));

        AdmissionResponseDto dto = new AdmissionResponseDto();
        dto.setId(admissionForm.getId());
        dto.setArbitrationClause(admissionForm.getArbitrationClause());
        dto.setDisputeAmount(admissionForm.getDisputeAmount());
        dto.setDisputeDate(admissionForm.getDisputeDate());
        dto.setDefaultClause(admissionForm.getDefaultClause());
        dto.setJurisdictionId(admissionForm.getJurisdictionId());
        dto.setJurisdictionName(admissionForm.getJurisdictionName());
        dto.setRefiefSought(admissionForm.getReliefSought());
        dto.setStatus(admissionForm.getStatus().name());
        dto.setCreatedAt(admissionForm.getCreatedAt());

        /* -------------------- CLAIMANTS -------------------- */
        List<ClaimantResponseDto> claimantDtos =
                claimantRepository.findByAdmissionFormId(admissionForm.getId())
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
                respondantRepository.findByAdmissionFormId(admissionForm.getId())
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
        AdmissionFormDocuments documents =
                admissionFormDocumentsRepository.findByAdmissionFormId(admissionForm.getId());

        if (documents != null) {
            DocumentsResponseDto docDto = new DocumentsResponseDto();
            docDto.setId(documents.getId());
            docDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
            docDto.setLrnDemandNotice(documents.getLrnDemandNotice());
            docDto.setAgreementContract(documents.getAgreementContract());
            docDto.setOrders(documents.getOrders());
            dto.setDocuments(docDto);
        }

        return dto;
    }


    @Override
    public GenericResponseDto createAdmission(CreateAdmissionRequestDto createAdmissionRequestDto) {

        AdmissionForm admissionForm = mapToAdmissionFormEntity(createAdmissionRequestDto);

        AdmissionForm savedAdmissionForm = admissionFormRepository.saveAndFlush(admissionForm);

        createAdmissionRequestDto.getClaimants().forEach(claimantRequestDto -> {
            AdmissionFormClaimant claimant = mapToClaimantEntity(savedAdmissionForm.getId(),claimantRequestDto);
            // Add admissionformuser to admissionformuser repository for existing user
            User user = userService.getUserByUserEmail(claimantRequestDto.getEmail());
            if(user != null){
                AdmissionFormUser admissionFormUser = new AdmissionFormUser();
                admissionFormUser.setAdmissionId(savedAdmissionForm.getId());
                admissionFormUser.setUserCaseType(UserCaseType.CLAIMANT);
                admissionFormUser.setUserId(user.getId());
                admissionFormUserRepository.save(admissionFormUser);
            }

            // Save claimant to database (you'll need a ClaimantRepository for this)
            claimantRepository.saveAndFlush(claimant);
        });

        createAdmissionRequestDto.getRespondants().forEach(respondantRequestDto -> {
            AdmissionFormRespondant respondant = mapToRespondantEntity(savedAdmissionForm.getId(),respondantRequestDto);
            // Add admissionformuser to admissionformuser repository for existing user
            User user = userService.getUserByUserEmail(respondant.getEmail());
            if(user != null){
                AdmissionFormUser admissionFormUser = new AdmissionFormUser();
                admissionFormUser.setAdmissionId(savedAdmissionForm.getId());
                admissionFormUser.setUserCaseType(UserCaseType.RESPONDANT);
                admissionFormUser.setUserId(user.getId());
                admissionFormUserRepository.save(admissionFormUser);
            }

            // Save claimant to database (you'll need a ClaimantRepository for this)
            respondantRepository.saveAndFlush(respondant);
        });

        CreateAdmissionDocumentsRequestDto documentsRequestDto = createAdmissionRequestDto.getDocuments();
        AdmissionFormDocuments admissionFormDocuments = mapToAdmissionFormDocumentsEntity(savedAdmissionForm.getId(),documentsRequestDto);
        admissionFormDocumentsRepository.saveAndFlush(admissionFormDocuments);

        String adminEmail = emailService.getAdminReceiverEmail();
        System.out.println(adminEmail);
        CreateEmailRequestDto createEmailRequestDto = new CreateEmailRequestDto();
        String messageBody = MailTemplate.generateAdmissionAdminEmail(savedAdmissionForm.getId());
        createEmailRequestDto.setMsgBody(messageBody);
        createEmailRequestDto.setRecipient(adminEmail);
        emailService.sendAdminMail(createEmailRequestDto);

        return new GenericResponseDto("success","Admission Created Successfully");
    }

    @Override
    public GenericResponseDto deleteAdmission(Long id) {

        if(admissionFormRepository.findById(id).isPresent()){
            claimantRepository.deletebyAdmissionFormId(id);
            respondantRepository.deletebyAdmissionFormId(id);
            admissionFormDocumentsRepository.deletebyAdmissionFormId(id);
            admissionFormRepository.deleteById(id);
            return new GenericResponseDto("success","Admission Deleted");
        }
        throw new ResourceNotFoundException("Admission","Id",id);
    }


    private AdmissionForm mapToAdmissionFormEntity(CreateAdmissionRequestDto createAdmissionRequestDto) {
        AdmissionForm dto = new AdmissionForm();
        dto.setArbitrationClause(createAdmissionRequestDto.getArbitrationClause());
        dto.setDisputeAmount(createAdmissionRequestDto.getDisputeAmount());
        dto.setDisputeDate(createAdmissionRequestDto.getDisputeDate());
        dto.setDefaultClause(createAdmissionRequestDto.getDefaultClause());
        dto.setJurisdictionId(createAdmissionRequestDto.getJurisdictionId());
        dto.setJurisdictionName(createAdmissionRequestDto.getJurisdictionName());
        dto.setReliefSought(createAdmissionRequestDto.getRefiefSought());
        dto.setStatus(FormStatus.valueOf(createAdmissionRequestDto.getStatus()));
        dto.setCreatedAt(LocalDate.now()); // Set creation date
        return dto;
    }

    private AdmissionFormClaimant mapToClaimantEntity(Long admissionFormId, CreateAdmissionClaimantRequestDto claimantDto) {
        AdmissionFormClaimant claimant = new AdmissionFormClaimant();
        claimant.setAdmissionFormId(admissionFormId);
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

    private AdmissionFormRespondant mapToRespondantEntity(Long admissionFormId, CreateAdmissionRespondantRequestDto respondantDto) {
        AdmissionFormRespondant respondant = new AdmissionFormRespondant();
        respondant.setAdmissionFormId(admissionFormId);
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

    private AdmissionFormDocuments mapToAdmissionFormDocumentsEntity(Long admissionFormId, CreateAdmissionDocumentsRequestDto documentsRequestDto) {
        AdmissionFormDocuments admissionFormDocuments = new AdmissionFormDocuments();
        admissionFormDocuments.setAdmissionFormId(admissionFormId);
        admissionFormDocuments.setPoaLoaIdCard(documentsRequestDto.getPoaLoaIdCard());
        admissionFormDocuments.setLrnDemandNotice(documentsRequestDto.getLrnDemandNotice());
        admissionFormDocuments.setAgreementContract(documentsRequestDto.getAgreementContract());
        admissionFormDocuments.setOrders(documentsRequestDto.getOrders());
        return admissionFormDocuments;
    }

}
