package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCountryRequestDto;
import com.mac.arbitrator.dto.request.create.CreateStateRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateCountryRequestDto;
import com.mac.arbitrator.dto.response.CountryResponseDto;
import com.mac.arbitrator.dto.response.mini.CountryMiniResponseDto;

import java.util.List;

public interface CountryService {
    GenericResponseDto create(CreateCountryRequestDto createCountryRequestDto);
    GenericResponseDto update(Long id,UpdateCountryRequestDto updateCountryRequestDto);
    GenericResponseDto delete(Long id);
    List<CountryMiniResponseDto> getAllCountryMini();
    CountryResponseDto getCountryById(Long id);
    List<CountryResponseDto> getAllCountries();
    GenericResponseDto bulkInsert(List<CreateCountryRequestDto> createCountryRequestDtos);
    Boolean checkAvailability(String name);
}
