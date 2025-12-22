package com.mac.arbitrator.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenericResponseDto {

    private String status;

    private String message;



}
