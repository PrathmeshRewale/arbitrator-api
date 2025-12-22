package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateCaseLogRequest;
import com.mac.arbitrator.service.CaseLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/apiv1/caselog")
@RequiredArgsConstructor
public class CaseLogController {
    private final CaseLogService caseLogService;

    @PostMapping()
    public ResponseEntity<?> createCaseLog(@RequestBody CreateCaseLogRequest createCaseLogRequest){
        return new ResponseEntity<GenericResponseDto>(caseLogService.create(createCaseLogRequest), OK);
    }
}
