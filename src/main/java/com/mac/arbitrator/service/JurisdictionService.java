package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateJurisdictionRequest;
import com.mac.arbitrator.dto.request.update.UpdateJurisdictionRequest;
import com.mac.arbitrator.dto.response.JurisdictionMiniResponseDto;
import com.mac.arbitrator.dto.response.JurisdictionResponseDto;

import java.util.List;

public interface JurisdictionService {
    List<JurisdictionResponseDto> getAllJurisdiction();
    List<JurisdictionMiniResponseDto> getAllMiniJurisdiction();
    JurisdictionResponseDto getById(Long id);
    GenericResponseDto createJurisdiction(CreateJurisdictionRequest createJurisdictionRequest);
    GenericResponseDto updatedJurisdiction(Long id,UpdateJurisdictionRequest updateJurisdictionRequest);
    GenericResponseDto deleteJurisdiction(Long id);
    boolean checkNameAvailable(String name);
}
