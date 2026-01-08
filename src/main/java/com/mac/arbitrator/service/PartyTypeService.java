package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequestDto;
import com.mac.arbitrator.dto.request.create.CreatePartyTypeRequestDto;
import com.mac.arbitrator.dto.request.update.UpdatePartyTypeRequestDto;
import com.mac.arbitrator.dto.response.PartyTypeResponseDto;
import com.mac.arbitrator.dto.response.mini.PartyTypeMiniResponseDto;

import java.util.List;

public interface PartyTypeService {
    GenericResponseDto create(CreatePartyTypeRequestDto createPartyTypeRequestDto);
    GenericResponseDto update(Long id,UpdatePartyTypeRequestDto updatePartyTypeRequestDto);
    GenericResponseDto delete(Long id);
    List<PartyTypeMiniResponseDto> getAllPartyTypeMini();
    PartyTypeResponseDto getPartyTypeById(Long id);
    List<PartyTypeResponseDto> getAllCountries();
    GenericResponseDto bulkInsert(List<CreatePartyTypeRequestDto> createPartyTypeRequestDtos);
    Boolean checkAvailability(String name);
}
