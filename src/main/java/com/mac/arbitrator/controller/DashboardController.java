package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.response.DashboardCountResponseDto;
import com.mac.arbitrator.dto.response.DashboardRecentActivityResponseDto;
import com.mac.arbitrator.dto.response.UpcomingHearingDetailsResponseDto;
import com.mac.arbitrator.service.DashboardService;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiv1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping(path = "/get_count")
    public ResponseEntity<?> getAllCounts(){
        return new ResponseEntity<DashboardCountResponseDto>(dashboardService.getDashboardCount(), OK);
    }

    @GetMapping(path = "/get_upcoming_hearing")
    public ResponseEntity<?> getNextUpcomingHearingDetails(){
        return new ResponseEntity<List<UpcomingHearingDetailsResponseDto>>(dashboardService.getAllUpcomingHearingDetails(),OK);
    }

    @GetMapping(path = "/get_recent_activity_details")
    public ResponseEntity<?> getRecentActivityDetails(){
        return new ResponseEntity<List<DashboardRecentActivityResponseDto>>(dashboardService.getDashboardRecentActivityResponsea(),OK);
    }
}
