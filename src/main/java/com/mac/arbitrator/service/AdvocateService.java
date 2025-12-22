package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdvocatePartyRequest;
import com.mac.arbitrator.dto.request.create.CreateAdvocateRequest;
import com.mac.arbitrator.dto.request.update.UpdateAdvocateRequest;
import com.mac.arbitrator.dto.response.AdvocateResponseDto;

import java.util.List;

public interface AdvocateService {
    GenericResponseDto createAdvocate(CreateAdvocateRequest createAdvocateRequest);
    GenericResponseDto assignAdvocateToClaimant(CreateAdvocatePartyRequest createAdvocatePartyRequest);
    GenericResponseDto assignAdvocateToRespondant(CreateAdvocatePartyRequest createAdvocatePartyRequest);
    List<AdvocateResponseDto> getAllAdvocates();
    GenericResponseDto updateAdvocate(Long id, UpdateAdvocateRequest updateAdvocateRequest);
    GenericResponseDto deleteAdvocate(Long id);
}
