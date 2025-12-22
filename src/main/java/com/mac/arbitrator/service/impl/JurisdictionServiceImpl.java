package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequest;
import com.mac.arbitrator.dto.request.update.UpdateJurisdictionRequest;
import com.mac.arbitrator.dto.response.JurisdictionMiniResponseDto;
import com.mac.arbitrator.dto.response.JurisdictionResponseDto;
import com.mac.arbitrator.entity.Jurisdiction;
import com.mac.arbitrator.repository.JurisdictionRepository;
import com.mac.arbitrator.service.JurisdictionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class JurisdictionServiceImpl implements JurisdictionService {

    private final JurisdictionRepository jurisdictionRepository;

    public JurisdictionServiceImpl(JurisdictionRepository jurisdictionRepository) {
        this.jurisdictionRepository = jurisdictionRepository;
    }

    @Override
    public List<JurisdictionResponseDto> getAllJurisdiction() {
        return jurisdictionRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<JurisdictionMiniResponseDto> getAllMiniJurisdiction() {
        return jurisdictionRepository.findAll().stream().map(this::mapTOMiniResponse).toList();
    }

    @Override
    public JurisdictionResponseDto getById(Long id) {
        Optional<Jurisdiction> jurisdiction = jurisdictionRepository.findById(id);
        if(jurisdiction.isPresent()){
            return mapToResponse(jurisdiction.get());
        }
        return null;
    }

    @Override
    public GenericResponseDto createJurisdiction(CreateJurisdictionRequest createJurisdictionRequest) {
        Jurisdiction jurisdiction = jurisdictionRepository.save(mapCreateEntityRequest(createJurisdictionRequest));
        return jurisdiction.getId() != null ? new GenericResponseDto("success","new jurisdiction created") : new GenericResponseDto("error","Something went wrong");
    }

    @Override
    public GenericResponseDto updatedJurisdiction(Long id, UpdateJurisdictionRequest updateJurisdictionRequest) {
        Optional<Jurisdiction> jurisdiction = jurisdictionRepository.findById(id);
        if(jurisdiction.isPresent()) {
            Jurisdiction jurisdiction1 = jurisdiction.get();
            mapUpdateEntityRequest(updateJurisdictionRequest,jurisdiction1);
            Jurisdiction jurisdiction2 = jurisdictionRepository.save(jurisdiction1);
            return jurisdiction2.getId() != null ? new GenericResponseDto("success","jurisdiction updated successfully") : new GenericResponseDto("error","Something went wrong");
        }
        return null;
    }

    @Override
    public GenericResponseDto deleteJurisdiction(Long id) {
        jurisdictionRepository.deleteById(id);
        return  new GenericResponseDto("success","jurisdiction deleted successfully");
    }

    @Override
    public boolean checkNameAvailable(String name) {
        return jurisdictionRepository.existsByName(name);
    }

    //helper methods
    JurisdictionResponseDto mapToResponse(Jurisdiction obj){
        if(obj == null){
            return null;
        }

        return JurisdictionResponseDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .description(obj.getDescription())
                .createdAt(obj.getCreatedAt())
                .createdByName(obj.getCreatedByName())
                .createdById(obj.getCreatedById())
                .updatedAt(obj.getUpdatedAt())
                .updatedByName(obj.getUpdatedByName())
                .updatedById(obj.getUpdatedById())
                .build();
    }

    JurisdictionMiniResponseDto mapTOMiniResponse(Jurisdiction obj){
        if(obj == null){
            return null;
        }

        return JurisdictionMiniResponseDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .build();
    }

    Jurisdiction mapCreateEntityRequest(CreateJurisdictionRequest req){
        if (req == null){
            return null;
        }
        return Jurisdiction.builder()
                .name(req.name())
                .createdAt(Instant.now())
                .createdById(req.createdById())
                .createdByName(req.createdByName())
                .description(req.description())
                .build();
    }

    void mapUpdateEntityRequest(UpdateJurisdictionRequest req, Jurisdiction obj){
        if (req == null){
            return;
        }
        obj.setUpdatedAt(Instant.now());
        obj.setUpdatedById(req.updatedById());
        obj.setUpdatedByName(req.updatedByName());
        obj.setName(req.name());
        obj.setDescription(req.description());
    }
}
