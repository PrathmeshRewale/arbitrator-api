package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.AdmissionRequestDto;
import com.mac.arbitrator.dto.request.ClaimantRequestDto;
import com.mac.arbitrator.dto.request.DocumentsRequestDto;
import com.mac.arbitrator.dto.request.RespondantRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.dto.response.ClaimantResponseDto;
import com.mac.arbitrator.dto.response.DocumentsResponseDto;
import com.mac.arbitrator.dto.response.RespondantResponseDto;
import com.mac.arbitrator.entity.AdmissionForm;
import com.mac.arbitrator.entity.AdmissionFormDocuments;
import com.mac.arbitrator.entity.Claimant;
import com.mac.arbitrator.entity.Respondant;
import com.mac.arbitrator.exception.ResourceNotFoundException;
import com.mac.arbitrator.repository.AdmissionFormDocumentsRepository;
import com.mac.arbitrator.repository.AdmissionFormRepository;
import com.mac.arbitrator.repository.ClaimantRepository;
import com.mac.arbitrator.repository.RespondantRepository;
import com.mac.arbitrator.service.AdmissionFormService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionFormServiceImpl implements AdmissionFormService {

    private AdmissionFormRepository admissionFormRepository;
    private AdmissionFormDocumentsRepository admissionFormDocumentsRepository;
    private ClaimantRepository claimantRepository;
    private RespondantRepository respondantRepository;

    public AdmissionFormServiceImpl(AdmissionFormRepository admissionFormRepository,
                                    AdmissionFormDocumentsRepository admissionFormDocumentsRepository,
                                    ClaimantRepository claimantRepository,
                                    RespondantRepository respondantRepository) {
        this.admissionFormRepository = admissionFormRepository;
        this.admissionFormDocumentsRepository = admissionFormDocumentsRepository;
        this.claimantRepository = claimantRepository;
        this.respondantRepository = respondantRepository;
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
                dto.setJurdisction(admissionForm.getJurdisction());
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
                dto.setJurdisction(admissionForm.getJurdisction());
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
        });

        admissionRequestDto.getRespondants().forEach(respondantRequestDto -> {
            Respondant respondant = mapToRespondantEntity(savedAdmissionForm.getId(),respondantRequestDto);
            // Save claimant to database (you'll need a ClaimantRepository for this)
            respondantRepository.saveAndFlush(respondant);
        });

        DocumentsRequestDto documentsRequestDto = admissionRequestDto.getDocuments();
        AdmissionFormDocuments admissionFormDocuments = mapToAdmissionFormDocumentsEntity(savedAdmissionForm.getId(),documentsRequestDto);
        admissionFormDocumentsRepository.saveAndFlush(admissionFormDocuments);

        return new GenericResponseDto("success","Admission Created Successfully");
    }

    @Override
    public GenericResponseDto updateAdmission(Long id, AdmissionRequestDto admissionRequestDto) {

        AdmissionForm admissionForm = admissionFormRepository.findById(id).orElseThrow();

        admissionForm.setArbitrationClause(admissionRequestDto.getArbitrationClause());
        admissionForm.setClaimAmount(admissionRequestDto.getClaimAmount());
        admissionForm.setDefaultClause(admissionRequestDto.getDefaultClause());
        admissionForm.setJurdisction(admissionRequestDto.getJurdisction());
        admissionForm.setRefiefSought(admissionRequestDto.getRefiefSought());
        admissionForm.setStatus(AdmissionForm.Status.valueOf(admissionRequestDto.getStatus()));

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



    private AdmissionForm mapToAdmissionFormEntity(AdmissionRequestDto admissionRequestDto){

        AdmissionForm admissionForm = new AdmissionForm();
        admissionForm.setArbitrationClause(admissionRequestDto.getArbitrationClause());
        admissionForm.setClaimAmount(admissionRequestDto.getClaimAmount());
        admissionForm.setDefaultClause(admissionRequestDto.getDefaultClause());
        admissionForm.setJurdisction(admissionRequestDto.getJurdisction());
        admissionForm.setRefiefSought(admissionRequestDto.getRefiefSought());
        admissionForm.setStatus(AdmissionForm.Status.valueOf(admissionRequestDto.getStatus()));

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
