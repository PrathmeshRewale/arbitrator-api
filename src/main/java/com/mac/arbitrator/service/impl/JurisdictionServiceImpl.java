package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateJurisdictionRequestDto;
import com.mac.arbitrator.dto.response.JurisdictionResponseDto;
import com.mac.arbitrator.dto.response.mini.JurisdictionMiniResponseDto;
import com.mac.arbitrator.entity.Jurisdiction;
import com.mac.arbitrator.repository.JurisdictionRepository;
import com.mac.arbitrator.service.JurisdictionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JurisdictionServiceImpl implements JurisdictionService {

    private final JurisdictionRepository jurisdictionRepository;

    public JurisdictionServiceImpl(JurisdictionRepository jurisdictionRepository) {
        this.jurisdictionRepository = jurisdictionRepository;
    }

    @Override
    public GenericResponseDto create(CreateJurisdictionRequestDto createJurisdictionRequestDto) {

        Jurisdiction jurisdiction = new Jurisdiction();
        jurisdiction.setName(createJurisdictionRequestDto.getName());
        jurisdiction.setDescription(createJurisdictionRequestDto.getDescription());
        jurisdiction.setCreatedAt(Instant.now());
        jurisdiction.setCreatedById(createJurisdictionRequestDto.getCreatedById());
        jurisdiction.setCreatedByName(createJurisdictionRequestDto.getCreatedByName());

        jurisdictionRepository.save(jurisdiction);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id,UpdateJurisdictionRequestDto updateJurisdictionRequestDto) {
        Jurisdiction jurisdiction = jurisdictionRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        jurisdiction.setName(updateJurisdictionRequestDto.getName());
        jurisdiction.setDescription(updateJurisdictionRequestDto.getDescription());
        jurisdiction.setUpdatedAt(Instant.now());
        jurisdiction.setUpdatedById(updateJurisdictionRequestDto.getUpdatedById());
        jurisdiction.setUpdatedByName(updateJurisdictionRequestDto.getUpdatedByName());

        jurisdictionRepository.save(jurisdiction);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public GenericResponseDto delete(Long id) {
        jurisdictionRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public List<JurisdictionMiniResponseDto> getAllJurisdictionMini() {
        return jurisdictionRepository.findAll().stream().map(obj->new JurisdictionMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public JurisdictionResponseDto getJurisdictionById(Long id) {
        Jurisdiction jurisdiction = jurisdictionRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        return mapToDto(jurisdiction);
    }

    @Override
    public List<JurisdictionResponseDto> getAllCountries() {
        return jurisdictionRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public GenericResponseDto bulkInsert(List<CreateJurisdictionRequestDto> createJurisdictionRequestDtos) {

        createJurisdictionRequestDtos.forEach(obj->{
            Jurisdiction jurisdiction = new Jurisdiction();
            jurisdiction.setName(obj.getName());
            jurisdiction.setDescription(obj.getDescription());
            jurisdiction.setCreatedAt(Instant.now());
            jurisdiction.setCreatedById(obj.getCreatedById());
            jurisdiction.setCreatedByName(obj.getCreatedByName());
            jurisdictionRepository.save(jurisdiction);
        });

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public Boolean checkAvailability(String name) {
        return jurisdictionRepository.existsByName(name);
    }

    private JurisdictionResponseDto mapToDto(Jurisdiction req){
        JurisdictionResponseDto res = new JurisdictionResponseDto();
        res.setId(req.getId());
        res.setName(req.getName());
        res.setDescription(req.getDescription());
        res.setCreatedAt(req.getCreatedAt());
        res.setCreatedById(req.getCreatedById());
        res.setCreatedByName(req.getCreatedByName());
        res.setUpdatedAt(req.getUpdatedAt());
        res.setUpdatedById(req.getUpdatedById());
        res.setUpdatedByName(req.getUpdatedByName());
        return res;
    }
}
