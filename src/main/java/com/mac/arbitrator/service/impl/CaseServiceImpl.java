package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.EmailRequest;
import com.mac.arbitrator.dto.request.create.CreateCaseRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseDocumentRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseMiniRequest;
import com.mac.arbitrator.dto.request.update.UpdateCaseRequest;
import com.mac.arbitrator.dto.response.*;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import com.mac.arbitrator.exception.UserNotFoundException;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.CaseService;
import com.mac.arbitrator.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final AdmissionFormRepository admissionFormRepository;
    private final CaseRepository caseRepository;
    private final ClaimantRepository claimantRepository;
    private final RespondantRepository respondantRepository;
    private final UserAdmissionFormRepository userAdmissionFormRepository;
    private final CaseLogRepository caseLogRepository;
    private final ClaimantAdvocateRepository claimantAdvocateRepository;
    private final RespondantAdvocateRepository respondentAdvocateRepository;
    private final ClaimantCaseDocumentsRepository claimantCaseDocumentsRepository;
    private final RespondantCaseDocumentsRepository respondantCaseDocumentsRepository;
    private final CaseHearingSchedulerRepository caseHearingSchedulerRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;


    @Override
    public GenericResponseDto registerCase(CreateCaseRequest createCaseRequest) {
        Optional<AdmissionForm> admissionFormOpt = admissionFormRepository.findById(createCaseRequest.admissionFormId());
        if (admissionFormOpt.isEmpty()) {
            return new GenericResponseDto("error", "Admission request not found");
        }
        // 2. Check if a case already exists for this admission form
        boolean caseExists = caseRepository.existsByAdmissionFormId(createCaseRequest.admissionFormId());
        if (caseExists) {
            return new GenericResponseDto("error", "A case already exists for this admission form.");
        }
        Case aCase = caseRepository.saveAndFlush(mapCreateRequestToEntity(createCaseRequest));
        Payment payment = paymentRepository.findByAdmissionId(createCaseRequest.admissionFormId());
        payment.setCaseId(aCase.getId());
        paymentRepository.save(payment);
        if(aCase.getId() != null){
            sendCaseCreationAdminEmail(aCase.getId());
        }
        return new GenericResponseDto("success", "Case registered successfully");
    }

    @Override
    public List<CaseResponseDto> getAllCases(Long userid) {

        List<UserAdmissionForm> forms = userAdmissionFormRepository.findAllByUserAdmissionFormEmbeddableUserid(userid);

        List<UserAdmissionFormEmbeddable> ids = forms.stream()
                .map(UserAdmissionForm::getUserAdmissionFormEmbeddable)
                .collect(Collectors.toList());

        List<CaseResponseDto> caseResponseMiniDtos = new ArrayList<>();

        ids.parallelStream().forEach(obj-> {
            List<Respondant> respondents = respondantRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            List<Claimant> claimants = claimantRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            List<String> repondentNames = respondents.parallelStream().map(Respondant::getFullName).toList();
            List<String> claimantNames = claimants.parallelStream().map(Claimant::getFullName).toList();
            Optional<Case> aCase = caseRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            aCase.ifPresent(value -> caseResponseMiniDtos.add(mapEntityToDto(value, repondentNames, claimantNames)));

        });

         return caseResponseMiniDtos;
    }

    @Override
    public List<CaseResponseDto> getAllCasesByUserIdAndType(Long userId, String type) {

        UserCaseType userCaseType = UserCaseType.valueOf(type.toUpperCase());

        List<UserAdmissionForm> forms = userAdmissionFormRepository.findAllByUserAdmissionFormEmbeddable_UseridAndUserAdmissionFormEmbeddable_UserCaseType(userId,userCaseType);

        List<UserAdmissionFormEmbeddable> ids = forms.stream()
                .map(UserAdmissionForm::getUserAdmissionFormEmbeddable)
                .collect(Collectors.toList());

        List<CaseResponseDto> caseResponseMiniDtos = new ArrayList<>();

        ids.parallelStream().forEach(obj-> {
            List<Respondant> respondents = respondantRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            List<Claimant> claimants = claimantRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            List<String> repondentNames = respondents.parallelStream().map(Respondant::getFullName).toList();
            List<String> claimantNames = claimants.parallelStream().map(Claimant::getFullName).toList();
            Optional<Case> aCase = caseRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            aCase.ifPresent(value -> caseResponseMiniDtos.add(mapEntityToDto(value, repondentNames, claimantNames)));

        });

        return caseResponseMiniDtos;
    }

    @Override
    public List<CaseResponseDto> getAllCasesByType(String type) {
        UserCaseType userCaseType = UserCaseType.valueOf(type.toUpperCase());

        List<UserAdmissionForm> forms = userAdmissionFormRepository.findAllByUserAdmissionFormEmbeddable_UserCaseType(userCaseType);

        List<UserAdmissionFormEmbeddable> ids = forms.stream()
                .map(UserAdmissionForm::getUserAdmissionFormEmbeddable)
                .collect(Collectors.toList());

        List<CaseResponseDto> caseResponseMiniDtos = new ArrayList<>();

        ids.parallelStream().forEach(obj-> {
            List<Respondant> respondents = respondantRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            List<Claimant> claimants = claimantRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            List<String> repondentNames = respondents.parallelStream().map(Respondant::getFullName).toList();
            List<String> claimantNames = claimants.parallelStream().map(Claimant::getFullName).toList();
            Optional<Case> aCase = caseRepository.findByAdmissionFormId(obj.getAdmissionFormId());
            aCase.ifPresent(value -> caseResponseMiniDtos.add(mapEntityToDto(value, repondentNames, claimantNames)));

        });

        return caseResponseMiniDtos;
    }

    @Override
    public CaseResponseDto getByCaseId(Long id) {

        Case aCase = caseRepository.findById(id).orElseThrow(()->new RuntimeException("case with id -> " + id + " not found"));

        List<Respondant> respondents = respondantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());
        List<Claimant> claimants = claimantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());
        List<String> repondentNames = respondents.parallelStream().map(Respondant::getFullName).toList();
        List<String> claimantNames = claimants.parallelStream().map(Claimant::getFullName).toList();

        return mapEntityToDto(aCase, repondentNames, claimantNames);
    }

    @Override
    public GenericResponseDto updateCaseStatusAndNumber(Long caseId, UpdateCaseMiniRequest updateCaseMiniRequest) {
        Case aCase = caseRepository.findById(caseId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));
        aCase.setStatus(updateCaseMiniRequest.status());
        aCase.setCaseNo(updateCaseMiniRequest.caseNo());
        aCase.setUpdatedById(updateCaseMiniRequest.updatedById());
        aCase.setUpdatedByName(updateCaseMiniRequest.updatedByName());
        aCase.setUpdatedAt(Instant.now());
        Case aCase1 = caseRepository.saveAndFlush(aCase);
        if(aCase1.getId() != null){
            if(aCase1.getStatus() != AdmissionFormStatus.APPROVED){
                sendClaimantRejectionMessage(aCase1.getCreatedById(),updateCaseMiniRequest.rejectionReason(),aCase1.getId());
            }else {
                sendAllPartyMailForCaseApproval(aCase1.getId());
            }
        }
        return new GenericResponseDto("success", "Case updated successfully");
    }

    @Override
    public GenericResponseDto updateCaseDocument(Long id, UpdateCaseDocumentRequest updateCaseDocumentRequest) {
        Case aCase = caseRepository.findById(id).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));
        aCase.setSection17DocPath(updateCaseDocumentRequest.section17DocPath());
        aCase.setStatementOfClaimPath(updateCaseDocumentRequest.statementOfClaimPath());
        aCase.setUpdatedById(updateCaseDocumentRequest.updatedById());
        aCase.setUpdatedByName(updateCaseDocumentRequest.updatedByName());
        aCase.setUpdatedAt(Instant.now());
        caseRepository.saveAndFlush(aCase);
        return new GenericResponseDto("success", "Case updated successfully");
    }

    @Override
    public Boolean checkIfCaseWithAdmissionIdAlreadyExist(Long id) {
        return caseRepository.existsByAdmissionFormId(id);
    }

    @Override
    public GenericResponseDto getCaseByUserId(Long userId) {
        // Step 1: Find all UserAdmissionForms by userId
        List<UserAdmissionForm> userAdmissionForms = userAdmissionFormRepository.findAllByUserAdmissionFormEmbeddableUserid(userId);

        // Step 2: Check if any case exists for any admissionFormId
        boolean caseExists = userAdmissionForms.stream()
                .map(form -> caseRepository.findByAdmissionFormId(form.getUserAdmissionFormEmbeddable().getAdmissionFormId()))
                .anyMatch(Optional::isPresent);

        // Step 3: If case exists, return success
        if (caseExists) {
            return new GenericResponseDto("success", "Case exists");
        } else {
            // Collect all admissionFormIds for the user
            List<Long> admissionFormIds = userAdmissionForms.stream()
                    .map(form -> form.getUserAdmissionFormEmbeddable().getAdmissionFormId())
                    .toList();

            // Assuming your GenericResponseDto supports an overloaded constructor or setter to pass this info
            // You might want to create a new constructor or a field for the list of IDs in GenericResponseDto
            return new GenericResponseDto("error", admissionFormIds.toString());
        }
    }

    @Override
    public CaseDetailResponseDto getCaseDetailsByCaseId(Long caseId) {

        Case aCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));

        List<Claimant> claimants = claimantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());
        List<Respondant> respondents = respondantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());

        List<ClaimantCaseDocuments> claimantDocuments = claimantCaseDocumentsRepository.findByCaseId(caseId);
        List<RespondantCaseDocuments> respondentDocuments = respondantCaseDocumentsRepository.findByCaseId(caseId);

        // ✅ Get Claimant Advocate (if exists)
        Optional<ClaimantAdvocate> claimantAdvocateOpt = claimantAdvocateRepository.findById_CaseId(caseId);
        String claimantAdvocateName = claimantAdvocateOpt.flatMap(ca ->
                userRepository.findById(ca.getId().getAdvocateId())
        ).map(User::getFullName).orElse("N/A");

        // ✅ Get Respondent Advocate (if exists)
        Optional<RespondantAdvocate> respondentAdvocateOpt = respondentAdvocateRepository.findById_CaseId(caseId);
        String respondentAdvocateName = respondentAdvocateOpt.flatMap(ra ->
                userRepository.findById(ra.getId().getAdvocateId())
        ).map(User::getFullName).orElse("N/A");

        // ✅ Get Case Logs (if any)
        List<CaseLog> caseLogs = Optional.ofNullable(caseLogRepository.findByCaseId(caseId))
                .orElseGet(List::of);

        // ✅ Get Case Hearing Schedule (if any)
        Optional<CaseHearingSchedule> hearingScheduleOpt =
                caseHearingSchedulerRepository.findTopByCaseIdOrderByNextHearingDesc(caseId);

        CaseHearingSchedule hearingSchedule = hearingScheduleOpt.orElse(null);

        // ✅ Map everything to DTO
        CaseDetailResponseDto response = mapToCaseDetailResponseDto(
                aCase,
                claimants,
                respondents,
                claimantAdvocateName,
                respondentAdvocateName,
                caseLogs,
                respondentDocuments,
                claimantDocuments
        );

        // ✅ Add hearing details if present
        if (hearingSchedule != null) {
            response.setHearingDetails(
                    CaseHearingScheduleResponseDto.builder()
                            .lastHearing(hearingSchedule.getLastHearing())
                            .nextHearing(hearingSchedule.getNextHearing())
                            .status(hearingSchedule.getStatus())
                            .build()
            );
        } else {
            // if not found, provide empty structure
            response.setHearingDetails(
                    CaseHearingScheduleResponseDto.builder()
                            .build()
            );
        }

        return response;
    }


    @Override
    public GenericResponseDto updateCaseDeatils(Long caseId, UpdateCaseRequest updateCaseRequest) {
        Case aCase = caseRepository.findById(caseId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));
        aCase.setCaseNo(updateCaseRequest.caseNo());
        aCase.setStatus(updateCaseRequest.status());
        aCase.setUpdatedAt(Instant.now());
        aCase.setUpdatedById(updateCaseRequest.updatedById());
        aCase.setUpdatedByName(updateCaseRequest.updatedByName());
        caseRepository.saveAndFlush(aCase);
        return new GenericResponseDto("success", "Case updated successfully");
    }


    private CaseDetailResponseDto mapToCaseDetailResponseDto(
            Case aCase,
            List<Claimant> claimants,
            List<Respondant> respondents,
            String claimantAdvocateName,
            String respondentAdvocateName,
            List<CaseLog> caseLogs,
            List<RespondantCaseDocuments> respondantCaseDocuments,
            List<ClaimantCaseDocuments> claimantCaseDocuments
    ) {
        CaseMiniResponseDto miniResponse = CaseMiniResponseDto.builder()
                .caseId(aCase.getId())
                .caseNo(aCase.getCaseNo())
                .caseStatus(aCase.getStatus())
                .createdAt(aCase.getCreatedAt())
                .build();

        String claimantName = claimants.isEmpty()
                ? "Unknown"
                : claimants.stream()
                .map(Claimant::getFullName)
                .collect(Collectors.joining(", "));

        String respondentName = respondents.isEmpty()
                ? "Unknown"
                : respondents.stream()
                .map(Respondant::getFullName)
                .collect(Collectors.joining(", "));

        // Map claimant documents
        List<PartyWithAdvocateResponseDto.PartyDocumentDetails> claimantDocs = claimantCaseDocuments.stream()
                .map(doc -> new PartyWithAdvocateResponseDto().new PartyDocumentDetails(
                        doc.getDocumentTitle(),
                        doc.getDocumentUrl(),
                        doc.getUploadedBy()
                ))
                .collect(Collectors.toList());

        // Map respondent documents
        List<PartyWithAdvocateResponseDto.PartyDocumentDetails> respondentDocs = respondantCaseDocuments.stream()
                .map(doc -> new PartyWithAdvocateResponseDto().new PartyDocumentDetails(
                        doc.getDocumentTitle(),
                        doc.getDocumentUrl(),
                        doc.getUploadedBy()
                ))
                .collect(Collectors.toList());


        PartyWithAdvocateResponseDto claimantsDto = PartyWithAdvocateResponseDto.builder()
                .partyName(claimantName)
                .advocateName(claimantAdvocateName)
                .documentUrls(claimantDocs)
                .build();

        PartyWithAdvocateResponseDto respondentsDto = PartyWithAdvocateResponseDto.builder()
                .partyName(respondentName)
                .advocateName(respondentAdvocateName)
                .documentUrls(respondentDocs)
                .build();

        List<CaseLogResponseDto> logDtos = caseLogs.stream()
                .map(log -> CaseLogResponseDto.builder()
                        .arbitrator(log.getArbitrator())
                        .nextHearingDate(log.getNextHearingDate())
                        .lastHearingDate(log.getLastHearingDate())
                        .purposeOfHearing(log.getPurposeOfHearing())
                        .attachment(log.getAttachment())
                        .createdAt(log.getCreatedAt())
                        .createdByName(log.getCreatedByName())
                        .createdById(log.getCreatedById())
                        .build())
                .toList();

        return CaseDetailResponseDto.builder()
                .caseMiniResponseDto(miniResponse)
                .claimantsAdvocate(claimantsDto)
                .respondentsAdvocate(respondentsDto)
                .caseLogs(logDtos)
                .build();
    }



    private Case mapCreateRequestToEntity(CreateCaseRequest createCaseRequest){
        return Case.builder()
                .caseNo(createCaseRequest.caseNo())
                .admissionFormId(createCaseRequest.admissionFormId())
                .status(createCaseRequest.status())
                .section17DocPath(createCaseRequest.section17DocPath())
                .statementOfClaimPath(createCaseRequest.statementOfClaimPath())
                .createdAt(Instant.now())
                .createdById(createCaseRequest.createdById())
                .createdByName(createCaseRequest.createdByName())
                .build();
    }

    private CaseResponseDto mapEntityToDto(Case aCase, List<String> respondentNames, List<String> claimantNames){
        return CaseResponseDto.builder()
                .id(aCase.getId())
                .caseNo(aCase.getCaseNo())
                .respondants(respondentNames)
                .claimants(claimantNames)
                .caseStatus(aCase.getStatus())
                .statementOfClaimPath(aCase.getStatementOfClaimPath())
                .section17DocPath(aCase.getSection17DocPath())
                .createdAt(aCase.getCreatedAt())
                .createdById(aCase.getCreatedById())
                .createdByName(aCase.getCreatedByName())
                .updatedAt(aCase.getUpdatedAt())
                .updatedById(aCase.getUpdatedById())
                .updatedByName(aCase.getUpdatedByName())
                .build();
    }

    private void sendCaseCreationAdminEmail(Long caseId){
        String fullName = "MAC";
        String adminEmail = emailService.getAdminReceiverEmail();
        String messagebody = EmailServiceImpl.generateCaseApprovalAdminEmail(fullName,caseId);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(adminEmail)
                .msgBody(messagebody)
                .build();
        emailService.sendAdminMail(emailRequest);
    }

    private void sendClaimantRejectionMessage(Long userid,String reason,Long caseid){
        User user = userRepository.findById(userid).orElseThrow(()->new RuntimeException("user with id -> " + userid + " not fount"));
        Case aCase = caseRepository.findById(caseid).orElseThrow(()-> new RuntimeException("case with id -> " + caseid + " not found"));
        String emailBody = EmailServiceImpl.generateCaseRejectionEmail(user.getFullName(),caseid,aCase.getAdmissionFormId(),reason);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .msgBody(emailBody)
                .build();
        emailService.sendClaimantMail(emailRequest);
    }

    private void sendAllPartyMailForCaseApproval(Long caseid){
        Case aCase = caseRepository.findById(caseid).orElseThrow(()-> new RuntimeException("case with id -> " + caseid + " not found"));
        List<Claimant> claimants = claimantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());
        List<Respondant> respondants = respondantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());

        claimants.forEach(obj->{
            String emailMessage = EmailServiceImpl.generateCaseApprovedForHearingEmail(obj.getFullName(),aCase.getCaseNo(),aCase.getAdmissionFormId(),UserCaseType.CLAIMANT);
            EmailRequest emailRequest = EmailRequest.builder()
                    .msgBody(emailMessage)
                    .recipient(obj.getEmail())
                    .build();

            emailService.sendClaimantMail(emailRequest);
        });
        respondants.forEach(obj->{
            String emailMessage = EmailServiceImpl.generateCaseApprovedForHearingEmail(obj.getFullName(),aCase.getCaseNo(),aCase.getAdmissionFormId(),UserCaseType.RESPONDANT);
            EmailRequest emailRequest = EmailRequest.builder()
                    .msgBody(emailMessage)
                    .recipient(obj.getEmail())
                    .build();

            emailService.sendRespondentMail(emailRequest);
        });
    }
}
