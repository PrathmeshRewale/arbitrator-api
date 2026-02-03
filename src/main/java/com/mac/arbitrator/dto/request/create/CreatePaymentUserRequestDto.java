package com.mac.arbitrator.dto.request.create;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentUserRequestDto {
    private Float amount;
    private String userName;
}
