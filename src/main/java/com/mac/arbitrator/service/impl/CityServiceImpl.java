package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCityRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateCityRequestDto;
import com.mac.arbitrator.dto.response.CityResponseDto;
import com.mac.arbitrator.dto.response.mini.CityMiniResponseDto;
import com.mac.arbitrator.entity.City;
import com.mac.arbitrator.repository.CityRepository;
import com.mac.arbitrator.repository.StateRepository;
import com.mac.arbitrator.service.CityService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public CityServiceImpl(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public GenericResponseDto create(CreateCityRequestDto createCityRequestDto) {

        City city = new City();
        city.setName(createCityRequestDto.getName());
        city.setStateId(createCityRequestDto.getStateId());
        city.setStateName(createCityRequestDto.getStateName());
        city.setCreatedAt(Instant.now());
        city.setCreatedById(createCityRequestDto.getCreatedById());
        city.setCreatedByName(createCityRequestDto.getCreatedByName());

        cityRepository.save(city);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id, UpdateCityRequestDto updateCityRequestDto) {
        City city = cityRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        city.setName(updateCityRequestDto.getName());
        city.setStateId(updateCityRequestDto.getStateId());
        city.setStateName(updateCityRequestDto.getStateName());
        city.setUpdatedAt(Instant.now());
        city.setUpdatedById(updateCityRequestDto.getUpdatedById());
        city.setUpdatedByName(updateCityRequestDto.getUpdatedByName());

        cityRepository.save(city);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public GenericResponseDto delete(Long id) {
        cityRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public List<CityMiniResponseDto> getAllCityMini() {
        return cityRepository.findAll().stream().map(obj->new CityMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public List<CityMiniResponseDto> getAllCityMiniByStateId(Long stateId) {
        return cityRepository.findAllByStateId(stateId).stream().map(obj->new CityMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public CityResponseDto getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        return mapToDto(city);
    }

    @Override
    public List<CityResponseDto> getAllCity() {
        return cityRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public GenericResponseDto bulkInsert(List<CreateCityRequestDto> createCityRequestDtos) {

        createCityRequestDtos.forEach(obj->{
            City city = new City();
            city.setName(obj.getName());
            city.setStateId(obj.getStateId());
            city.setStateName(obj.getStateName());
            city.setCreatedAt(Instant.now());
            city.setCreatedById(obj.getCreatedById());
            city.setCreatedByName(obj.getCreatedByName());
            cityRepository.save(city);
        });

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public Boolean checkAvailability(String name) {
        return cityRepository.existsByName(name);
    }

    @Override
    public void deleteByStateId(Long countryId) {
        cityRepository.deleteAllByStateId(countryId);
    }

    @Modifying
    @Transactional
    @Override
    public void deleteByCountryId(Long countryId) {
        stateRepository.findAllByCountryId(countryId).forEach(obj->{
            deleteByStateId(obj.getId());
        });
    }

    private CityResponseDto mapToDto(City req){
        CityResponseDto res = new CityResponseDto();
        res.setId(req.getId());
        res.setName(req.getName());
        res.setStateId(req.getStateId());
        res.setStateName(req.getStateName());
        res.setCreatedAt(req.getCreatedAt());
        res.setCreatedById(req.getCreatedById());
        res.setCreatedByName(req.getCreatedByName());
        res.setUpdatedAt(req.getUpdatedAt());
        res.setUpdatedById(req.getUpdatedById());
        res.setUpdatedByName(req.getUpdatedByName());
        return res;
    }
}
