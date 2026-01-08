package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCountryRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateCountryRequestDto;
import com.mac.arbitrator.dto.response.CountryResponseDto;
import com.mac.arbitrator.dto.response.mini.CountryMiniResponseDto;
import com.mac.arbitrator.entity.Country;
import com.mac.arbitrator.repository.CountryRepository;
import com.mac.arbitrator.service.CityService;
import com.mac.arbitrator.service.CountryService;
import com.mac.arbitrator.service.StateService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final StateService stateService;
    private final CityService cityService;

    public CountryServiceImpl(CountryRepository countryRepository, StateService stateService, CityService cityService) {
        this.countryRepository = countryRepository;
        this.stateService = stateService;
        this.cityService = cityService;
    }

    @Override
    public GenericResponseDto create(CreateCountryRequestDto createCountryRequestDto) {

        Country country = new Country();
        country.setName(createCountryRequestDto.getName());
        country.setCreatedAt(Instant.now());
        country.setCreatedById(createCountryRequestDto.getCreatedById());
        country.setCreatedByName(createCountryRequestDto.getCreatedByName());

        countryRepository.save(country);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id, UpdateCountryRequestDto updateCountryRequestDto) {
        Country country = countryRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        country.setName(updateCountryRequestDto.getName());
        country.setUpdatedAt(Instant.now());
        country.setUpdatedById(updateCountryRequestDto.getUpdatedById());
        country.setUpdatedByName(updateCountryRequestDto.getUpdatedByName());

        countryRepository.save(country);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Modifying
    @Transactional
    @Override
    public GenericResponseDto delete(Long id) {
        cityService.deleteByCountryId(id);
        stateService.deleteByCountryId(id);
        countryRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public List<CountryMiniResponseDto> getAllCountryMini() {
        return countryRepository.findAll().stream().map(obj->new CountryMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public CountryResponseDto getCountryById(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        return mapToDto(country);
    }

    @Override
    public List<CountryResponseDto> getAllCountries() {
        return countryRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public GenericResponseDto bulkInsert(List<CreateCountryRequestDto> createCountryRequestDtos) {

        createCountryRequestDtos.forEach(obj->{
            Country country = new Country();
            country.setName(obj.getName());
            country.setCreatedAt(Instant.now());
            country.setCreatedById(obj.getCreatedById());
            country.setCreatedByName(obj.getCreatedByName());
            countryRepository.save(country);
        });

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public Boolean checkAvailability(String name) {
        return countryRepository.existsByName(name);
    }

    private CountryResponseDto mapToDto(Country req){
        CountryResponseDto res = new CountryResponseDto();
        res.setId(req.getId());
        res.setName(req.getName());
        res.setCreatedAt(req.getCreatedAt());
        res.setCreatedById(req.getCreatedById());
        res.setCreatedByName(req.getCreatedByName());
        res.setUpdatedAt(req.getUpdatedAt());
        res.setUpdatedById(req.getUpdatedById());
        res.setUpdatedByName(req.getUpdatedByName());
        return res;
    }
}
