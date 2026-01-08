package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateStateRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateStateRequestDto;
import com.mac.arbitrator.dto.response.StateResponseDto;
import com.mac.arbitrator.dto.response.mini.StateMiniResponseDto;

import java.util.List;

public interface StateService {
    GenericResponseDto create(CreateStateRequestDto createStateRequestDto);
    GenericResponseDto update(Long id,UpdateStateRequestDto updateStateRequestDto);
    GenericResponseDto delete(Long id);
    List<StateMiniResponseDto> getAllStateMini();
    StateResponseDto getStateById(Long id);
    List<StateResponseDto> getAllCountries();
    GenericResponseDto bulkInsert(List<CreateStateRequestDto> createStateRequestDtos);
    Boolean checkAvailability(String name);
    void deleteByCountryId(Long countryId);
}
