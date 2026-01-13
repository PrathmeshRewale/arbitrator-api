package com.mac.arbitrator.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecentActivityDto {
    private String module; // ADMISSION, USERS, CASE
    private String title;
}
