package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.AdmissionRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.service.AdmissionFormService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionFormServiceImpl implements AdmissionFormService {

    @Override
    public List<AdmissionResponseDto> getAllAdmissions() {



        return null;
    }

    @Override
    public AdmissionResponseDto getAdmissionById(Long id) {
        return null;
    }

    @Override
    public GenericResponseDto createAdmission(AdmissionRequestDto admissionRequestDto) {
        return new GenericResponseDto("success","Admission Created Successfully");
    }

    @Override
    public GenericResponseDto updateAdmission(Long id, AdmissionRequestDto admissionRequestDto) {
        return null;
    }

    @Override
    public GenericResponseDto deleteAdmission(Long id) {
        return null;
    }
}
