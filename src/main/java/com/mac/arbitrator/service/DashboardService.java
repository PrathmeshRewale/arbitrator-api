package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.response.dashboard.DashboardResponseDto;

public interface DashboardService {
    DashboardResponseDto getDashboardData();
    DashboardResponseDto getAdminDashboard();
    DashboardResponseDto getArbitratorDashboard(Long id);
    DashboardResponseDto getUserDashboard(Long id);

}
