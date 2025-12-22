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
public class DashboardRecentActivityResponseDto {
    private LocalDate date;
    private String type;
    private String id;
    private String details;
}
