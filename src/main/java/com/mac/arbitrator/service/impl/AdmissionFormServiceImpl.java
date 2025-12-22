package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.*;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.dto.response.ClaimantResponseDto;
import com.mac.arbitrator.dto.response.DocumentsResponseDto;
import com.mac.arbitrator.dto.response.RespondantResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.exception.ResourceNotFoundException;
import com.mac.arbitrator.exception.UserNotFoundException;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.AdmissionFormService;
import com.mac.arbitrator.service.EmailService;
import com.mac.arbitrator.service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdmissionFormServiceImpl implements AdmissionFormService {

    private AdmissionFormRepository admissionFormRepository;
    private AdmissionFormDocumentsRepository admissionFormDocumentsRepository;
    private ClaimantRepository claimantRepository;
    private RespondantRepository respondantRepository;
    private UserAdmissionFormRepository userAdmissionFormRepository;
    private UserRepository userRepository;
    private EmailService emailService;
    private PaymentService paymentService;

    public AdmissionFormServiceImpl(AdmissionFormRepository admissionFormRepository, AdmissionFormDocumentsRepository admissionFormDocumentsRepository, ClaimantRepository claimantRepository, RespondantRepository respondantRepository, UserAdmissionFormRepository userAdmissionFormRepository, UserRepository userRepository, EmailService emailService, PaymentService paymentService, SettingRepository settingRepository, ObjectMapper objectMapper) {
        this.admissionFormRepository = admissionFormRepository;
        this.admissionFormDocumentsRepository = admissionFormDocumentsRepository;
        this.claimantRepository = claimantRepository;
        this.respondantRepository = respondantRepository;
        this.userAdmissionFormRepository = userAdmissionFormRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.paymentService = paymentService;
    }

    @Override
    public List<AdmissionResponseDto> getAllAdmissions() {


        List<AdmissionForm> admissionForms = admissionFormRepository.findAll();
        if(!admissionForms.isEmpty()){
            // Map entities to DTOs
            List<AdmissionResponseDto> admissionResponseDtos = admissionForms.stream().map(admissionForm -> {
                AdmissionResponseDto dto = new AdmissionResponseDto();

                dto.setId(admissionForm.getId());
                dto.setArbitrationClause(admissionForm.getArbitrationClause());
                dto.setClaimAmount(admissionForm.getClaimAmount());
                dto.setDefaultClause(admissionForm.getDefaultClause());
                dto.setJurisdictionId(admissionForm.getJurisdictionId());
                dto.setJurisdictionName(admissionForm.getJurisdictionName());
                dto.setRefiefSought(admissionForm.getRefiefSought());
                dto.setStatus(admissionForm.getStatus().name());

                List<Claimant> claimants = claimantRepository.findByAdmissionFormId(admissionForm.getId());
                List<ClaimantResponseDto> claimantDtos = claimants.stream().map(claimant -> {
                    ClaimantResponseDto claimantDto = new ClaimantResponseDto();
                    claimantDto.setId(claimant.getId());
                    claimantDto.setFullName(claimant.getFullName());
                    claimantDto.setEmail(claimant.getEmail());
                    claimantDto.setPhoneNumber(claimant.getPhoneNumber());
                    claimantDto.setCountry(claimant.getCountry());
                    claimantDto.setState(claimant.getState());
                    claimantDto.setCity(claimant.getCity());
                    claimantDto.setZipCode(claimant.getZipCode());
                    claimantDto.setAddress(claimant.getAddress());
                    return claimantDto;
                }).toList();
                dto.setClaimants(claimantDtos);


                List<Respondant> respondants = respondantRepository.findByAdmissionFormId(admissionForm.getId());
                List<RespondantResponseDto> respondantDtos = respondants.stream().map(respondant -> {
                    RespondantResponseDto respondantDto = new RespondantResponseDto();
                    respondantDto.setId(respondant.getId());
                    respondantDto.setFullName(respondant.getFullName());
                    respondantDto.setEmail(respondant.getEmail());
                    respondantDto.setPhoneNumber(respondant.getPhoneNumber());
                    respondantDto.setCountry(respondant.getCountry());
                    respondantDto.setState(respondant.getState());
                    respondantDto.setCity(respondant.getCity());
                    respondantDto.setZipCode(respondant.getZipCode());
                    respondantDto.setAddress(respondant.getAddress());
                    return respondantDto;
                }).toList();
                dto.setRespondants(respondantDtos);


                AdmissionFormDocuments documents = admissionFormDocumentsRepository.findByAdmissionFormId(admissionForm.getId());
                if(documents != null){
                    DocumentsResponseDto documentsDto = new DocumentsResponseDto();
                    documentsDto.setId(documents.getId());
                    documentsDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                    documentsDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                    documentsDto.setAgreementContract(documents.getAgreementContract());
                    documentsDto.setOrders(documents.getOrders());
                    dto.setDocuments(documentsDto);
                }



                return dto;
            }).toList();

            return admissionResponseDtos;
        }

        return null;
    }

    @Override
    public AdmissionResponseDto getAdmissionById(Long id) {

        AdmissionForm admissionForm = admissionFormRepository.findById(id).orElseThrow();
        if(admissionForm != null){
            // Map entities to DTOs

                AdmissionResponseDto dto = new AdmissionResponseDto();

                dto.setId(admissionForm.getId());
                dto.setArbitrationClause(admissionForm.getArbitrationClause());
                dto.setClaimAmount(admissionForm.getClaimAmount());
                dto.setDefaultClause(admissionForm.getDefaultClause());
                dto.setJurisdictionId(admissionForm.getJurisdictionId());
                dto.setRefiefSought(admissionForm.getRefiefSought());
                dto.setStatus(admissionForm.getStatus().name());

                List<Claimant> claimants = claimantRepository.findByAdmissionFormId(admissionForm.getId());
                List<ClaimantResponseDto> claimantDtos = claimants.stream().map(claimant -> {
                    ClaimantResponseDto claimantDto = new ClaimantResponseDto();
                    claimantDto.setId(claimant.getId());
                    claimantDto.setFullName(claimant.getFullName());
                    claimantDto.setEmail(claimant.getEmail());
                    claimantDto.setPhoneNumber(claimant.getPhoneNumber());
                    claimantDto.setCountry(claimant.getCountry());
                    claimantDto.setState(claimant.getState());
                    claimantDto.setCity(claimant.getCity());
                    claimantDto.setZipCode(claimant.getZipCode());
                    claimantDto.setAddress(claimant.getAddress());
                    return claimantDto;
                }).toList();
                dto.setClaimants(claimantDtos);


                List<Respondant> respondants = respondantRepository.findByAdmissionFormId(admissionForm.getId());
                List<RespondantResponseDto> respondantDtos = respondants.stream().map(respondant -> {
                    RespondantResponseDto respondantDto = new RespondantResponseDto();
                    respondantDto.setId(respondant.getId());
                    respondantDto.setFullName(respondant.getFullName());
                    respondantDto.setEmail(respondant.getEmail());
                    respondantDto.setPhoneNumber(respondant.getPhoneNumber());
                    respondantDto.setCountry(respondant.getCountry());
                    respondantDto.setState(respondant.getState());
                    respondantDto.setCity(respondant.getCity());
                    respondantDto.setZipCode(respondant.getZipCode());
                    respondantDto.setAddress(respondant.getAddress());
                    return respondantDto;
                }).toList();
                dto.setRespondants(respondantDtos);


                AdmissionFormDocuments documents = admissionFormDocumentsRepository.findByAdmissionFormId(admissionForm.getId());
                if(documents != null){
                    DocumentsResponseDto documentsDto = new DocumentsResponseDto();
                    documentsDto.setId(documents.getId());
                    documentsDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                    documentsDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                    documentsDto.setAgreementContract(documents.getAgreementContract());
                    documentsDto.setOrders(documents.getOrders());
                    dto.setDocuments(documentsDto);
                }



                return dto;


        }

        return null;
    }

    @Override
    public GenericResponseDto createAdmission(AdmissionRequestDto admissionRequestDto) {

        AdmissionForm admissionForm = mapToAdmissionFormEntity(admissionRequestDto);

        AdmissionForm savedAdmissionForm = admissionFormRepository.saveAndFlush(admissionForm);

        admissionRequestDto.getClaimants().forEach(claimantRequestDto -> {
            Claimant claimant = mapToClaimantEntity(savedAdmissionForm.getId(),claimantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            claimantRepository.saveAndFlush(claimant);

            // Associate user by email
            Optional<User> userOptional = userRepository.findByEmail(claimantRequestDto.getEmail()); //dont check directly put
            userOptional.ifPresent(user -> {
                UserAdmissionForm admissionForm1 = new UserAdmissionForm();
                admissionForm1.setUserAdmissionFormEmbeddable(
                        new UserAdmissionFormEmbeddable(user.getId(), savedAdmissionForm.getId(), UserCaseType.CLAIMANT)
                );
                userAdmissionFormRepository.saveAndFlush(admissionForm1);
            });
        });

        admissionRequestDto.getRespondants().forEach(respondantRequestDto -> {
            Respondant respondant = mapToRespondantEntity(savedAdmissionForm.getId(),respondantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            respondantRepository.saveAndFlush(respondant);

            // Associate user by email
            Optional<User> userOptional = userRepository.findByEmail(respondantRequestDto.getEmail());
            userOptional.ifPresent(user -> {
                UserAdmissionForm admissionForm1 = new UserAdmissionForm();
                admissionForm1.setUserAdmissionFormEmbeddable(
                        new UserAdmissionFormEmbeddable(user.getId(), savedAdmissionForm.getId(),UserCaseType.RESPONDANT)
                );
                userAdmissionFormRepository.saveAndFlush(admissionForm1);
            });
        });

        DocumentsRequestDto documentsRequestDto = admissionRequestDto.getDocuments();
        AdmissionFormDocuments admissionFormDocuments = mapToAdmissionFormDocumentsEntity(savedAdmissionForm.getId(),documentsRequestDto);
        admissionFormDocumentsRepository.saveAndFlush(admissionFormDocuments);
        String receiverEmail = emailService.getAdminReceiverEmail();
        String message = EmailServiceImpl.generateRegistrationEmail(receiverEmail,savedAdmissionForm.getId());
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(receiverEmail)
                .msgBody(message)
                .build();

        emailService.sendAdminMail(emailRequest);
        return new GenericResponseDto("success","Admission Created Successfully");
    }

    @Override
    public GenericResponseDto updateAdmission(Long id, AdmissionRequestDto admissionRequestDto) {

        AdmissionForm admissionForm = admissionFormRepository.findById(id).orElseThrow();

        admissionForm.setArbitrationClause(admissionRequestDto.getArbitrationClause());
        admissionForm.setClaimAmount(admissionRequestDto.getClaimAmount());
        admissionForm.setDefaultClause(admissionRequestDto.getDefaultClause());
        admissionForm.setJurisdictionId(admissionRequestDto.getJurisdictionId());
        admissionForm.setRefiefSought(admissionRequestDto.getRefiefSought());
        admissionForm.setStatus(AdmissionFormStatus.valueOf(admissionRequestDto.getStatus()));

        admissionFormRepository.saveAndFlush(admissionForm);


        claimantRepository.deletebyAdmissionFormId(admissionForm.getId());

        admissionRequestDto.getClaimants().forEach(claimantRequestDto -> {
            Claimant claimant = mapToClaimantEntity(admissionForm.getId(),claimantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            claimantRepository.saveAndFlush(claimant);
        });

        respondantRepository.deletebyAdmissionFormId(admissionForm.getId());

        admissionRequestDto.getRespondants().forEach(respondantRequestDto -> {
            Respondant respondant = mapToRespondantEntity(admissionForm.getId(),respondantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            respondantRepository.saveAndFlush(respondant);
        });

        admissionFormDocumentsRepository.deletebyAdmissionFormId(admissionForm.getId());

        DocumentsRequestDto documentsRequestDto = admissionRequestDto.getDocuments();
        AdmissionFormDocuments admissionFormDocuments = mapToAdmissionFormDocumentsEntity(admissionForm.getId(),documentsRequestDto);
        admissionFormDocumentsRepository.saveAndFlush(admissionFormDocuments);



        return null;
    }

    @Override
    public GenericResponseDto deleteAdmission(Long id) {

        if(admissionFormRepository.findById(id).isPresent()){
            admissionFormRepository.deleteById(id);
            return new GenericResponseDto("success","Admission Deleted");
        }
        throw new ResourceNotFoundException("Admission","Id",id);
    }


    @Override
    public List<AdmissionResponseDto> getAllAdmissions(Long userId) {

        List<UserAdmissionForm> userAdmissionForms = userAdmissionFormRepository.findAllByUserAdmissionFormEmbeddableUserid(userId);

        if (userAdmissionForms.isEmpty()) {
            return Collections.emptyList(); // Return empty list instead of null
        }

        List<Long> admissionIds = userAdmissionForms.stream()
                .map(uaf -> uaf.getUserAdmissionFormEmbeddable().getAdmissionFormId())
                .toList();

        List<AdmissionForm> admissionForms = admissionFormRepository.findAllById(admissionIds);
        if(!admissionForms.isEmpty()){
            // Map entities to DTOs
            List<AdmissionResponseDto> admissionResponseDtos = admissionForms.stream().map(admissionForm -> {
                AdmissionResponseDto dto = new AdmissionResponseDto();

                dto.setId(admissionForm.getId());
                dto.setArbitrationClause(admissionForm.getArbitrationClause());
                dto.setClaimAmount(admissionForm.getClaimAmount());
                dto.setDefaultClause(admissionForm.getDefaultClause());
                dto.setJurisdictionId(admissionForm.getJurisdictionId());
                dto.setRefiefSought(admissionForm.getRefiefSought());
                dto.setStatus(admissionForm.getStatus().name());

                List<Claimant> claimants = claimantRepository.findByAdmissionFormId(admissionForm.getId());
                List<ClaimantResponseDto> claimantDtos = claimants.stream().map(claimant -> {
                    ClaimantResponseDto claimantDto = new ClaimantResponseDto();
                    claimantDto.setId(claimant.getId());
                    claimantDto.setFullName(claimant.getFullName());
                    claimantDto.setEmail(claimant.getEmail());
                    claimantDto.setPhoneNumber(claimant.getPhoneNumber());
                    claimantDto.setCountry(claimant.getCountry());
                    claimantDto.setState(claimant.getState());
                    claimantDto.setCity(claimant.getCity());
                    claimantDto.setZipCode(claimant.getZipCode());
                    claimantDto.setAddress(claimant.getAddress());
                    return claimantDto;
                }).toList();
                dto.setClaimants(claimantDtos);


                List<Respondant> respondants = respondantRepository.findByAdmissionFormId(admissionForm.getId());
                List<RespondantResponseDto> respondantDtos = respondants.stream().map(respondant -> {
                    RespondantResponseDto respondantDto = new RespondantResponseDto();
                    respondantDto.setId(respondant.getId());
                    respondantDto.setFullName(respondant.getFullName());
                    respondantDto.setEmail(respondant.getEmail());
                    respondantDto.setPhoneNumber(respondant.getPhoneNumber());
                    respondantDto.setCountry(respondant.getCountry());
                    respondantDto.setState(respondant.getState());
                    respondantDto.setCity(respondant.getCity());
                    respondantDto.setZipCode(respondant.getZipCode());
                    respondantDto.setAddress(respondant.getAddress());
                    return respondantDto;
                }).toList();
                dto.setRespondants(respondantDtos);


                AdmissionFormDocuments documents = admissionFormDocumentsRepository.findByAdmissionFormId(admissionForm.getId());
                if(documents != null){
                    DocumentsResponseDto documentsDto = new DocumentsResponseDto();
                    documentsDto.setId(documents.getId());
                    documentsDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                    documentsDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                    documentsDto.setAgreementContract(documents.getAgreementContract());
                    documentsDto.setOrders(documents.getOrders());
                    dto.setDocuments(documentsDto);
                }



                return dto;
            }).toList();

            return admissionResponseDtos;
        }

        return null;
    }

    @Override
    public GenericResponseDto updateAdmissionStatus(Long userId, Long admissionId,String status) {
        AdmissionForm admissionForm = admissionFormRepository.findById(admissionId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Admission with given ID not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "User with given ID not found"));

        admissionForm.setStatus(AdmissionFormStatus.valueOf(status.toUpperCase()));
        admissionFormRepository.saveAndFlush(admissionForm);

        return new GenericResponseDto("success", "Admission status updated successfully");
    }

    @Override
    public AdmissionResponseDto getAdmissionDetailsByIdAndUserEmail(Long admissionId, String userEmail) {
        AdmissionForm admissionForm = admissionFormRepository.findById(admissionId).orElseThrow(()->new UserNotFoundException(NOT_FOUND,"Admission with id not found"));

        //check if the user is present as claiment or respondant in the admission form

        //get all cliaments by admission id
        List<Claimant> claimants = claimantRepository.findByAdmissionFormId(admissionId);

        //get all respondant by admission id
        List<Respondant> respondants = respondantRepository.findByAdmissionFormId(admissionId);


        boolean isUserLinkedToAdmission = claimants.stream().anyMatch(claimant ->
                claimant.getEmail().equalsIgnoreCase(userEmail)
        ) || respondants.stream().anyMatch(respondant ->
                respondant.getEmail().equalsIgnoreCase(userEmail)
        );

        if(admissionForm != null){
            // Map entities to DTOs
            if(!isUserLinkedToAdmission){
                throw new UserNotFoundException(HttpStatus.NOT_FOUND,"You are not associated with this admission");
            }

            AdmissionResponseDto dto = new AdmissionResponseDto();

            dto.setId(admissionForm.getId());
            dto.setArbitrationClause(admissionForm.getArbitrationClause());
            dto.setClaimAmount(admissionForm.getClaimAmount());
            dto.setDefaultClause(admissionForm.getDefaultClause());
            dto.setJurisdictionId(admissionForm.getJurisdictionId());
            dto.setRefiefSought(admissionForm.getRefiefSought());
            dto.setStatus(admissionForm.getStatus().name());


            List<ClaimantResponseDto> claimantDtos = claimants.stream().map(claimant -> {
                ClaimantResponseDto claimantDto = new ClaimantResponseDto();
                claimantDto.setId(claimant.getId());
                claimantDto.setFullName(claimant.getFullName());
                claimantDto.setEmail(claimant.getEmail());
                claimantDto.setPhoneNumber(claimant.getPhoneNumber());
                claimantDto.setCountry(claimant.getCountry());
                claimantDto.setState(claimant.getState());
                claimantDto.setCity(claimant.getCity());
                claimantDto.setZipCode(claimant.getZipCode());
                claimantDto.setAddress(claimant.getAddress());
                return claimantDto;
            }).toList();
            dto.setClaimants(claimantDtos);



            List<RespondantResponseDto> respondantDtos = respondants.stream().map(respondant -> {
                RespondantResponseDto respondantDto = new RespondantResponseDto();
                respondantDto.setId(respondant.getId());
                respondantDto.setFullName(respondant.getFullName());
                respondantDto.setEmail(respondant.getEmail());
                respondantDto.setPhoneNumber(respondant.getPhoneNumber());
                respondantDto.setCountry(respondant.getCountry());
                respondantDto.setState(respondant.getState());
                respondantDto.setCity(respondant.getCity());
                respondantDto.setZipCode(respondant.getZipCode());
                respondantDto.setAddress(respondant.getAddress());
                return respondantDto;
            }).toList();
            dto.setRespondants(respondantDtos);


            AdmissionFormDocuments documents = admissionFormDocumentsRepository.findByAdmissionFormId(admissionForm.getId());
            if(documents != null){
                DocumentsResponseDto documentsDto = new DocumentsResponseDto();
                documentsDto.setId(documents.getId());
                documentsDto.setPoaLoaIdCard(documents.getPoaLoaIdCard());
                documentsDto.setLrnDemandNotice(documents.getLrnDemandNotice());
                documentsDto.setAgreementContract(documents.getAgreementContract());
                documentsDto.setOrders(documents.getOrders());
                dto.setDocuments(documentsDto);
            }
            return dto;
        }
        return null;
    }

    @Override
    public GenericResponseDto updateAdmissionAmountAndStatus(AdmissionPaymentRequest admissionRequestDto) {
        AdmissionForm admissionForm = admissionFormRepository.findById(admissionRequestDto.getAdmissionId()).orElseThrow(()->new UserNotFoundException(NOT_FOUND,"Admission with id not found"));
        admissionForm.setStatus(admissionRequestDto.getStatus());
        admissionFormRepository.save(admissionForm);
        return paymentService.assignUserPayment(admissionRequestDto);
    }




    private AdmissionForm mapToAdmissionFormEntity(AdmissionRequestDto admissionRequestDto){

        AdmissionForm admissionForm = new AdmissionForm();
        admissionForm.setArbitrationClause(admissionRequestDto.getArbitrationClause());
        admissionForm.setClaimAmount(admissionRequestDto.getClaimAmount());
        admissionForm.setDefaultClause(admissionRequestDto.getDefaultClause());
        admissionForm.setJurisdictionId(admissionRequestDto.getJurisdictionId());
        admissionForm.setJurisdictionName(admissionRequestDto.getJurisdictionName());
        admissionForm.setRefiefSought(admissionRequestDto.getRefiefSought());
        admissionForm.setStatus(AdmissionFormStatus.valueOf(admissionRequestDto.getStatus()));
        admissionForm.setCreatedAt(Instant.now());

        return admissionForm;
    }

    private Claimant mapToClaimantEntity(Long admission_form_id,ClaimantRequestDto claimantDto){
        Claimant claimant = new Claimant();
        claimant.setAdmissionFormId(admission_form_id);
        claimant.setFullName(claimantDto.getFullName());
        claimant.setPhoneNumber(claimantDto.getPhoneNumber());
        claimant.setEmail(claimantDto.getEmail());
        claimant.setCountry(claimantDto.getCountry());
        claimant.setState(claimantDto.getState());
        claimant.setCity(claimantDto.getCity());
        claimant.setZipCode(claimantDto.getZipCode());
        claimant.setPhoneNumber(claimantDto.getPhoneNumber());
        claimant.setAddress(claimantDto.getAddress());
        claimant.setSecondaryAddress(claimantDto.getSecondaryAddress());
        claimant.setSecondaryCity(claimantDto.getSecondaryCity());
        claimant.setSecondaryState(claimantDto.getSecondaryState());
        claimant.setSecondaryEmail(claimantDto.getSecondaryEmail());
        claimant.setSecondaryCountry(claimantDto.getSecondaryCountry());
        claimant.setSecondaryZipCode(claimantDto.getSecondaryZipCode());
        return claimant;
    }

    private Respondant mapToRespondantEntity(Long admission_form_id, RespondantRequestDto respondantDto){
        Respondant respondant = new Respondant();
        respondant.setAdmissionFormId(admission_form_id);
        respondant.setFullName(respondantDto.getFullName());
        respondant.setPhoneNumber(respondantDto.getPhoneNumber());
        respondant.setEmail(respondantDto.getEmail());
        respondant.setCountry(respondantDto.getCountry());
        respondant.setState(respondantDto.getState());
        respondant.setCity(respondantDto.getCity());
        respondant.setZipCode(respondantDto.getZipCode());
        respondant.setPhoneNumber(respondantDto.getPhoneNumber());
        respondant.setAddress(respondantDto.getAddress());
        respondant.setSecondaryAddress(respondantDto.getSecondaryAddress());
        respondant.setSecondaryState(respondantDto.getSecondaryState());
        respondant.setSecondaryEmail(respondantDto.getSecondaryEmail());
        respondant.setSecondaryCity(respondantDto.getSecondaryCity());
        respondant.setSecondaryZipCode(respondantDto.getSecondaryZipCode());
        respondant.setSecondaryCountry(respondantDto.getSecondaryCountry());
        return respondant;
    }

    private AdmissionFormDocuments mapToAdmissionFormDocumentsEntity(Long admission_form_id, DocumentsRequestDto documentsRequestDto){
        AdmissionFormDocuments admissionFormDocuments = new AdmissionFormDocuments();
        admissionFormDocuments.setAdmissionFormId(admission_form_id);
        admissionFormDocuments.setPoaLoaIdCard(documentsRequestDto.getPoaLoaIdCard());
        admissionFormDocuments.setLrnDemandNotice(documentsRequestDto.getLrnDemandNotice());
        admissionFormDocuments.setAgreementContract(documentsRequestDto.getAgreementContract());
        admissionFormDocuments.setOrders(documentsRequestDto.getOrders());
        return admissionFormDocuments;
    }
}
