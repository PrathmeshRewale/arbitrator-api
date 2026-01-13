package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateArbitratorRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateArbitratorPasswordRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateArbitratorRequestDto;
import com.mac.arbitrator.dto.response.ArbitratorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArbitratorService {
    GenericResponseDto create(CreateArbitratorRequestDto createArbitratorRequestDto);
    GenericResponseDto update(Long id, UpdateArbitratorRequestDto updateArbitratorRequestDto);
    GenericResponseDto delete(Long id);
    GenericResponseDto bulkInsert(List<CreateArbitratorRequestDto> createArbitratorRequestDtos);
    Page<ArbitratorResponseDto> findAll(Pageable pageable);
    GenericResponseDto resetArbitratorPassword(UpdateArbitratorPasswordRequestDto updateArbitratorPasswordRequestDto);
}
