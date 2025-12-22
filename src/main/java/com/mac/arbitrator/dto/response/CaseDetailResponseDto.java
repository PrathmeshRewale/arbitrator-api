package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseDetailResponseDto {
    private CaseMiniResponseDto caseMiniResponseDto;
    private PartyWithAdvocateResponseDto claimantsAdvocate;
    private PartyWithAdvocateResponseDto respondentsAdvocate;
    private List<CaseLogResponseDto> caseLogs;
    private CaseHearingScheduleResponseDto hearingDetails;
}
