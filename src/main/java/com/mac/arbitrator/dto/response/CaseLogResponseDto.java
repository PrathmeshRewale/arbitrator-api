package com.mac.arbitrator.dto.response;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseLogResponseDto {
    private String arbitrator;
    private LocalDate lastHearingDate;
    private LocalDate nextHearingDate;
    private String purposeOfHearing;
    private String attachment;
    private Long createdById;
    private Instant createdAt;
    private String createdByName;
}
