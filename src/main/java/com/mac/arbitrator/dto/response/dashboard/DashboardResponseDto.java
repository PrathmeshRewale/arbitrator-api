package com.mac.arbitrator.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardResponseDto {

    private Long totalCases;
    private Long newAdmissions;
    private Long newMediations;
    private Long activeUsers;
    private Long totalArbitrators;

    private List<UpcomingHearingDto> upcomingHearings;
    private List<RecentActivityDto> recentActivities;
}
