package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardCountResponseDto {
    private Integer totalCases;
    private Integer totalNewAdmission;
    private Integer activeUser;
    private Integer totalAdvocates;
}
