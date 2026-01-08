package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateMediationRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateMediationFormStatus;
import com.mac.arbitrator.dto.response.AdmissionResponseDto;
import com.mac.arbitrator.dto.response.MediationResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MediationFormService {
    Page<MediationResponseDto> getAllMediations(int page, int size);
    List<MediationResponseDto> getAllMediations();

    MediationResponseDto getMediationById(Long id);

    GenericResponseDto createMediation(CreateMediationRequestDto createMediationRequestDto);

    GenericResponseDto deleteMediation(Long id);

}
