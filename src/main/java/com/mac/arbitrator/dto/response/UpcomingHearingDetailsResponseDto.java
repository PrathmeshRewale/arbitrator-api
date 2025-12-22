package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpcomingHearingDetailsResponseDto {
    private Long caseId;
    private LocalDate nextHearing;
    private String status;
}
