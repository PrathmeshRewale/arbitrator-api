package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.FormStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdmissionFormUserPaymentRequestDto {
    private Long admissionFormId;
    private FormStatus status;
    private Float amount;
    private Float remainingAmount;
    List<AdmissionFormUserAmountDetails> admissionFormUserAmountDetails;

    @Data
    public static class AdmissionFormUserAmountDetails{
        private UserCaseType type;
        private String userEmail;
        private Float amount;
    }

}
