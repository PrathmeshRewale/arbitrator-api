package com.mac.arbitrator.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartyWithAdvocateResponseDto {
    private String partyName;
    private String advocateName;
    private List<PartyDocumentDetails> documentUrls;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class PartyDocumentDetails{
        private String documenttitle;
        private String documentUrls;
        private String uploadedBy;
    }
}
