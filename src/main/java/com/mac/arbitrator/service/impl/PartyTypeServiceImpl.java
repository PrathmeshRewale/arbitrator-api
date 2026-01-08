package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreatePartyTypeRequestDto;
import com.mac.arbitrator.dto.request.update.UpdatePartyTypeRequestDto;
import com.mac.arbitrator.dto.response.PartyTypeResponseDto;
import com.mac.arbitrator.dto.response.mini.PartyTypeMiniResponseDto;
import com.mac.arbitrator.entity.PartyType;
import com.mac.arbitrator.repository.PartyTypeRepository;
import com.mac.arbitrator.service.PartyTypeService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PartyTypeServiceImpl implements PartyTypeService {

    private final PartyTypeRepository partyTypeRepository;

    public PartyTypeServiceImpl(PartyTypeRepository partyTypeRepository) {
        this.partyTypeRepository = partyTypeRepository;
    }

    @Override
    public GenericResponseDto create(CreatePartyTypeRequestDto createPartyTypeRequestDto) {

        PartyType partyType = new PartyType();
        partyType.setName(createPartyTypeRequestDto.getName());
        partyType.setDescription(createPartyTypeRequestDto.getDescription());
        partyType.setCreatedAt(Instant.now());
        partyType.setCreatedById(createPartyTypeRequestDto.getCreatedById());
        partyType.setCreatedByName(createPartyTypeRequestDto.getCreatedByName());

        partyTypeRepository.save(partyType);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id, UpdatePartyTypeRequestDto updatePartyTypeRequestDto) {
        PartyType partyType = partyTypeRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        partyType.setName(updatePartyTypeRequestDto.getName());
        partyType.setDescription(updatePartyTypeRequestDto.getDescription());
        partyType.setUpdatedAt(Instant.now());
        partyType.setUpdatedById(updatePartyTypeRequestDto.getUpdatedById());
        partyType.setUpdatedByName(updatePartyTypeRequestDto.getUpdatedByName());

        partyTypeRepository.save(partyType);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public GenericResponseDto delete(Long id) {
        partyTypeRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public List<PartyTypeMiniResponseDto> getAllPartyTypeMini() {
        return partyTypeRepository.findAll().stream().map(obj->new PartyTypeMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public PartyTypeResponseDto getPartyTypeById(Long id) {
        PartyType partyType = partyTypeRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        return mapToDto(partyType);
    }

    @Override
    public List<PartyTypeResponseDto> getAllCountries() {
        return partyTypeRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public GenericResponseDto bulkInsert(List<CreatePartyTypeRequestDto> createPartyTypeRequestDtos) {

        createPartyTypeRequestDtos.forEach(obj->{
            PartyType partyType = new PartyType();
            partyType.setName(obj.getName());
            partyType.setDescription(obj.getDescription());
            partyType.setCreatedAt(Instant.now());
            partyType.setCreatedById(obj.getCreatedById());
            partyType.setCreatedByName(obj.getCreatedByName());
            partyTypeRepository.save(partyType);
        });

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public Boolean checkAvailability(String name) {
        return partyTypeRepository.existsByName(name);
    }

    private PartyTypeResponseDto mapToDto(PartyType req){
        PartyTypeResponseDto res = new PartyTypeResponseDto();
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
