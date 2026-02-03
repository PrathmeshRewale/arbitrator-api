package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateStateRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateStateRequestDto;
import com.mac.arbitrator.dto.response.StateResponseDto;
import com.mac.arbitrator.dto.response.mini.StateMiniResponseDto;
import com.mac.arbitrator.entity.State;
import com.mac.arbitrator.repository.StateRepository;
import com.mac.arbitrator.service.CityService;
import com.mac.arbitrator.service.StateService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final CityService cityService;

    public StateServiceImpl(StateRepository stateRepository, CityService cityService) {
        this.stateRepository = stateRepository;
        this.cityService = cityService;
    }

    @Override
    public GenericResponseDto create(CreateStateRequestDto createStateRequestDto) {

        State state = new State();
        state.setName(createStateRequestDto.getName());
        state.setCountryId(createStateRequestDto.getCountryId());
        state.setCountryName(createStateRequestDto.getCountryName());
        state.setCreatedAt(Instant.now());
        state.setCreatedById(createStateRequestDto.getCreatedById());
        state.setCreatedByName(createStateRequestDto.getCreatedByName());

        stateRepository.save(state);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id, UpdateStateRequestDto updateStateRequestDto) {
        State state = stateRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        state.setName(updateStateRequestDto.getName());
        state.setCountryId(updateStateRequestDto.getCountryId());
        state.setCountryName(updateStateRequestDto.getCountryName());
        state.setUpdatedAt(Instant.now());
        state.setUpdatedById(updateStateRequestDto.getUpdatedById());
        state.setUpdatedByName(updateStateRequestDto.getUpdatedByName());

        stateRepository.save(state);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Modifying
    @Transactional
    @Override
    public GenericResponseDto delete(Long id) {
        cityService.deleteByStateId(id);
        stateRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public List<StateMiniResponseDto> getAllStateMini() {
        return stateRepository.findAll().stream().map(obj->new StateMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public List<StateMiniResponseDto> getAllStateMiniByCountryId(Long countryId) {
        return stateRepository.findAllByCountryId(countryId).stream().map(obj->new StateMiniResponseDto(obj.getId(), obj.getName())).toList();
    }

    @Override
    public StateResponseDto getStateById(Long id) {
        State state = stateRepository.findById(id).orElseThrow(()->new RuntimeException("record with id not found"));
        return mapToDto(state);
    }

    @Override
    public List<StateResponseDto> getAllState() {
        return stateRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public GenericResponseDto bulkInsert(List<CreateStateRequestDto> createStateRequestDtos) {

        createStateRequestDtos.forEach(obj->{
            State state = new State();
            state.setName(obj.getName());
            state.setCountryId(obj.getCountryId());
            state.setCountryName(obj.getCountryName());
            state.setCreatedAt(Instant.now());
            state.setCreatedById(obj.getCreatedById());
            state.setCreatedByName(obj.getCreatedByName());
            stateRepository.save(state);
        });

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public Boolean checkAvailability(String name) {
        return stateRepository.existsByName(name);
    }

    @Override
    public void deleteByCountryId(Long countryId) {
        stateRepository.deleteAllByCountryId(countryId);
    }

    private StateResponseDto mapToDto(State req){
        StateResponseDto res = new StateResponseDto();
        res.setId(req.getId());
        res.setName(req.getName());
        res.setCountryId(req.getCountryId());
        res.setCountryName(req.getCountryName());
        res.setCreatedAt(req.getCreatedAt());
        res.setCreatedById(req.getCreatedById());
        res.setCreatedByName(req.getCreatedByName());
        res.setUpdatedAt(req.getUpdatedAt());
        res.setUpdatedById(req.getUpdatedById());
        res.setUpdatedByName(req.getUpdatedByName());
        return res;
    }
}
