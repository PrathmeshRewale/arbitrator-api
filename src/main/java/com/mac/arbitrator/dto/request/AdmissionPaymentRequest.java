package com.mac.arbitrator.dto.request;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;
import com.mac.arbitrator.entity.enums.UserCaseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionPaymentRequest {
    private Long admissionId;
    private AdmissionFormStatus status;
    private Float totalAmount;
    private List<UserAdmountDetails> userAdmountDetails;

    @Data
    public static class UserAdmountDetails{
        private UserCaseType type;
        private String userEmail;
        private Float amount;
    }
}
