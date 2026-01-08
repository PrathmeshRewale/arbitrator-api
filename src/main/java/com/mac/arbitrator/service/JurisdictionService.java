package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateJurisdictionRequestDto;
import com.mac.arbitrator.dto.response.JurisdictionResponseDto;
import com.mac.arbitrator.dto.response.mini.JurisdictionMiniResponseDto;

import java.util.List;

public interface JurisdictionService {
    GenericResponseDto create(CreateJurisdictionRequestDto createJurisdictionRequestDto);
    GenericResponseDto update(Long id,UpdateJurisdictionRequestDto updateJurisdictionRequestDto);
    GenericResponseDto delete(Long id);
    List<JurisdictionMiniResponseDto> getAllJurisdictionMini();
    JurisdictionResponseDto getJurisdictionById(Long id);
    List<JurisdictionResponseDto> getAllCountries();
    GenericResponseDto bulkInsert(List<CreateJurisdictionRequestDto> createJurisdictionRequestDtos);
    Boolean checkAvailability(String name);
}
