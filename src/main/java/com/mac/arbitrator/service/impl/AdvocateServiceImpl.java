package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdvocatePartyRequest;
import com.mac.arbitrator.dto.request.create.CreateAdvocateRequest;
import com.mac.arbitrator.dto.request.update.UpdateAdvocateRequest;
import com.mac.arbitrator.dto.response.AdvocateResponseDto;
import com.mac.arbitrator.entity.*;
import com.mac.arbitrator.entity.enums.UserType;
import com.mac.arbitrator.exception.UserNotFoundException;
import com.mac.arbitrator.repository.*;
import com.mac.arbitrator.service.AdvocateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AdvocateServiceImpl implements AdvocateService {

    private final UserRepository userrepository;
    private final ClaimantAdvocateRepository claimantAdvocateRepository;
    private final RespondantAdvocateRepository respondentAdvocateRepository;
    private final CaseRepository caseRepository;
    private final ClaimantRepository claimantRepository;
    private final RespondantRepository respondantRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public GenericResponseDto createAdvocate(CreateAdvocateRequest createAdvocateRequest) {
        // 1. Check if the role "ARBITRATOR" exists
        Optional<Role> roleOptional = roleRepository.findByName("ARBITRATOR");
        if (roleOptional.isEmpty()) {
            return new GenericResponseDto("error", "Arbitrator role not found");
        }

        Role role = roleOptional.get();

        try {
            // 2. Check for unique email
            if (userrepository.existsByEmail(createAdvocateRequest.email())) {
                return new GenericResponseDto("error", "Email already exists");
            }

            // 3. Check for unique username
            if (userrepository.existsByUsername(createAdvocateRequest.username())) {
                return new GenericResponseDto("error", "Username already exists");
            }

            // 4. Check for unique bar registration number
            if (userrepository.existsByBarRegistrationNumber(createAdvocateRequest.barRegistrationNumber())) {
                return new GenericResponseDto("error", "Bar registration number already exists");
            }

            // 5. Map the request to entity and save
            User advocate = mapToCreateAdvocate(createAdvocateRequest, role.getId());
            userrepository.saveAndFlush(advocate);

            return new GenericResponseDto("success", "Advocate added successfully");

        } catch (Exception e) {
            // Catch any unexpected exception (like DB constraint violation)
            return new GenericResponseDto("error", "Failed to create advocate: " + e.getMessage());
        }
    }


    @Override
    public GenericResponseDto assignAdvocateToClaimant(CreateAdvocatePartyRequest createAdvocatePartyRequest) {
        Case aCase = caseRepository.findById(createAdvocatePartyRequest.caseId()).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));
        List<Claimant> claimants = claimantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());
       claimants.forEach(obj->{
           ClaimantAdvocateEmbeddable embeddableId = new ClaimantAdvocateEmbeddable(createAdvocatePartyRequest.caseId(), obj.getId(), createAdvocatePartyRequest.advocateId());
           ClaimantAdvocate entity = new ClaimantAdvocate(embeddableId);
           claimantAdvocateRepository.saveAndFlush(entity);
       });
        return new GenericResponseDto("success","Advocate assigned to claimants");
    }

    @Override
    public GenericResponseDto assignAdvocateToRespondant(CreateAdvocatePartyRequest createAdvocatePartyRequest) {
        Case aCase = caseRepository.findById(createAdvocatePartyRequest.caseId()).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Case with given ID not found"));
        List<Respondant> respondants = respondantRepository.findByAdmissionFormId(aCase.getAdmissionFormId());
        respondants.forEach(obj->{
            RespondantAdvocateEmbeddable respondantAdvocateEmbeddable = new RespondantAdvocateEmbeddable(createAdvocatePartyRequest.caseId(),obj.getId(),createAdvocatePartyRequest.advocateId());
            RespondantAdvocate advocate = new RespondantAdvocate(respondantAdvocateEmbeddable);
            respondentAdvocateRepository.saveAndFlush(advocate);
        });
        return new GenericResponseDto("success","Advocate assigned to respondants");
    }

    @Override
    public List<AdvocateResponseDto> getAllAdvocates() {
        List<User> users =  userrepository.findAll();
        List<AdvocateResponseDto> advocateResponseDtos = new ArrayList<>();
        users.forEach(obj-> {
            if(obj.getBarRegistrationNumber() != null){
                advocateResponseDtos.add(mapToResponse(obj));
            }
        });

        return  advocateResponseDtos;
    }

    @Override
    public GenericResponseDto updateAdvocate(Long id,UpdateAdvocateRequest updateAdvocateRequest) {
        Optional<Role> role = roleRepository.findByName("ARBITRATOR");
        if(role.isPresent()) {
            User user = userrepository.findById(id).orElseThrow(()->new UserNotFoundException(NOT_FOUND,"Advocate with id -> " +id +" nor found"));
            mapToUpdateAdvocate(updateAdvocateRequest,role.get().getId(),user);
            user.setId(id);
            userrepository.saveAndFlush(user);
            return new GenericResponseDto("success", "Advocate updated successfully");
        }
        return new GenericResponseDto("error","Arbitrator role not found");
    }

    @Override
    public GenericResponseDto deleteAdvocate(Long id) {
        return userrepository.findById(id).map(advocate -> {
            userrepository.deleteById(id);
            return new GenericResponseDto("success", "Advocate deleted successfully");
        }).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "Advocate not found"));
    }


    public AdvocateResponseDto mapToResponse(User advocate) {
        if (advocate == null) {
            return null;
        }

        return AdvocateResponseDto.builder()
                .id(advocate.getId())
                .username(advocate.getUsername())
                .password(advocate.getPassword())
                .roleId(advocate.getRoleId() != null ? advocate.getRoleId() : null)
                .fullName(advocate.getFullName())
                .barRegistrationNumber(advocate.getBarRegistrationNumber())
                .email(advocate.getEmail())
                .phoneNumber(advocate.getPhoneNo())
                .enrollmentDate(advocate.getEnrollmentDate())
                .gender(advocate.getGender())
                .jurisdictionId(advocate.getJurisdictionId() != null ? advocate.getJurisdictionId() : null)
                .jurisdictionName(advocate.getJurisdictionName())
                .createdAt(advocate.getCreatedAt())
                .createdById(advocate.getCreatedById() != null ? advocate.getCreatedById() : null)
                .createdByName(advocate.getCreatedByName() != null ? advocate.getCreatedByName() : null)
                .updatedAt(advocate.getUpdatedAt())
                .updatedById(advocate.getUpdatedById() != null ? advocate.getUpdatedById() : null)
                .updatedByName(advocate.getUpdatedByName() != null ? advocate.getUpdatedByName() : null)
                .build();
    }



    private User mapToCreateAdvocate(CreateAdvocateRequest dto,Long roleid) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .username(dto.username())
                .fullName(dto.fullName())
                .roleId(roleid)
                .phoneNo(dto.phoneNumber())
                .jurisdictionId(dto.jurisdictionId())
                .jurisdictionName(dto.jurisdictionName())
                .barRegistrationNumber(dto.barRegistrationNumber())
                .password(passwordEncoder.encode(dto.password()))
                .gender(dto.gender())
                .email(dto.email())
                .enrollmentDate(dto.enrollmentDate())
                .createdAt(Instant.now())
                .createdByName(dto.createdByName())
                .createdById(dto.createdById())
                .userType(UserType.ARBITRATOR)
                .build();
    }

    private void mapToUpdateAdvocate(UpdateAdvocateRequest dto,Long roleId,User user) {
        if (dto == null) {
            return;
        }
        user.setFullName(dto.fullName());
        user.setUpdatedById(dto.updatedById());
        user.setUpdatedAt(Instant.now());
        user.setUpdatedByName(dto.updatedByName());
        user.setEmail(dto.email());
        user.setPhoneNo(dto.phoneNumber());
        user.setGender(dto.gender());
        user.setBarRegistrationNumber(dto.barRegistrationNumber());
        user.setEnrollmentDate(dto.enrollmentDate());
        user.setJurisdictionId(dto.jurisdictionId());
        user.setJurisdictionName(dto.jurisdictionName());
    }

}
