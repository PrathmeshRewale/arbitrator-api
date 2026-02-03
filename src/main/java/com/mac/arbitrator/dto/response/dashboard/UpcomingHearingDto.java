package com.mac.arbitrator.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpcomingHearingDto {
    private Long caseId;
    private String caseNo;
    private LocalDate nextHearingDate;
    private String arbitratorName;
}
