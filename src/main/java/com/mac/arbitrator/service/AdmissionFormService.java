package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdmissionRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdmissionFormService {
    Page<AdmissionResponseDto> getAllAdmissions(int page, int size);
    List<AdmissionResponseDto> getAllAdmissions();
    AdmissionResponseDto getAdmissionById(Long id);
    GenericResponseDto createAdmission(CreateAdmissionRequestDto createAdmissionRequestDto);
    GenericResponseDto deleteAdmission(Long id);
}
