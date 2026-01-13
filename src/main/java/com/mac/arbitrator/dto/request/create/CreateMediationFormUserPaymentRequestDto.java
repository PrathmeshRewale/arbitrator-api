package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.FormStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMediationFormUserPaymentRequestDto {
    private Long mediationFormId;
    private FormStatus status;
    private Float amount;
    private Float remainingAmount;
    List<MediationFormUserAmountDetails> mediationFormUserAmountDetails;
    private LocalDate createdAt;

    @Data
    public static class MediationFormUserAmountDetails{
        private UserCaseType type;
        private String userEmail;
        private Float amount;
    }
}
