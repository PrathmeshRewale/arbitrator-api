package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.AdmissionPaymentRequest;
import com.mac.arbitrator.dto.request.AdmissionRequestDto;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdmissionFormService {


    List<AdmissionResponseDto> getAllAdmissions();

    AdmissionResponseDto getAdmissionById(Long id);

    GenericResponseDto createAdmission(AdmissionRequestDto admissionRequestDto);

    GenericResponseDto updateAdmission(Long id, AdmissionRequestDto admissionRequestDto);

    GenericResponseDto deleteAdmission(Long id);

    List<AdmissionResponseDto> getAllAdmissions(Long userId);

    GenericResponseDto updateAdmissionStatus(Long userId, Long admissionId,String status);

    AdmissionResponseDto getAdmissionDetailsByIdAndUserEmail(Long admissionId, String userEmail);

    GenericResponseDto updateAdmissionAmountAndStatus(AdmissionPaymentRequest admissionRequestDto);
}
