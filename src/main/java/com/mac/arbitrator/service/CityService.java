package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCityRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateCityRequestDto;
import com.mac.arbitrator.dto.response.CityResponseDto;
import com.mac.arbitrator.dto.response.mini.CityMiniResponseDto;

import java.util.List;

public interface CityService {
    GenericResponseDto create(CreateCityRequestDto createCityRequestDto);
    GenericResponseDto update(Long id,UpdateCityRequestDto updateCityRequestDto);
    GenericResponseDto delete(Long id);
    List<CityMiniResponseDto> getAllCityMini();
    CityResponseDto getCityById(Long id);
    List<CityResponseDto> getAllCity();
    GenericResponseDto bulkInsert(List<CreateCityRequestDto> createCityRequestDtos);
    Boolean checkAvailability(String name);
    void deleteByStateId(Long stateId);
    void deleteByCountryId(Long countryId);
}
