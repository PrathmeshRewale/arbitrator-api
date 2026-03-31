package com.mac.arbitrator.controller;


import com.mac.arbitrator.dto.response.dashboard.DashboardResponseDto;
import com.mac.arbitrator.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {


    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    public ResponseEntity<DashboardResponseDto> getAdminDashboard() {
        return ResponseEntity.ok(dashboardService.getAdminDashboard());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardResponseDto> getUserDashboard(@PathVariable Long userId) {
        return ResponseEntity.ok(dashboardService.getUserDashboard(userId));
    }

    @GetMapping("/arbitrator/{userId}")
    public ResponseEntity<DashboardResponseDto> getArbitratorDashboard(@PathVariable Long userId) {
        return ResponseEntity.ok(dashboardService.getArbitratorDashboard(userId));
    }
}
