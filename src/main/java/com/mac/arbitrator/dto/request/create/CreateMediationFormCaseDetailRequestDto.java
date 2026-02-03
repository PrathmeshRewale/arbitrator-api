package com.mac.arbitrator.dto.request.create;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMediationFormCaseDetailRequestDto {
    private Long mediationFormId;
    private LocalDateTime dateOfHearing;
    private String zoomLink;
}
