package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.response.DashboardCountResponseDto;
import com.mac.arbitrator.dto.response.DashboardRecentActivityResponseDto;
import com.mac.arbitrator.dto.response.UpcomingHearingDetailsResponseDto;

import java.util.List;

public interface DashboardService {
    DashboardCountResponseDto getDashboardCount();
    List<UpcomingHearingDetailsResponseDto> getAllUpcomingHearingDetails();
    List<DashboardRecentActivityResponseDto> getDashboardRecentActivityResponsea();
}
