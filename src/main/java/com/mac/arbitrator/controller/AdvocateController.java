package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateAdvocatePartyRequest;
import com.mac.arbitrator.dto.request.create.CreateAdvocateRequest;
import com.mac.arbitrator.dto.request.update.UpdateAdvocateRequest;
import com.mac.arbitrator.dto.response.AdvocateResponseDto;
import com.mac.arbitrator.service.AdvocateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/apiv1/advocate")
@RequiredArgsConstructor
public class AdvocateController {

    private final AdvocateService advocateService;

    @PostMapping()
    public ResponseEntity<?> createAdvocate(@RequestBody CreateAdvocateRequest createAdvocateRequest){
        return new ResponseEntity<GenericResponseDto>(advocateService.createAdvocate(createAdvocateRequest), OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateAdvocate(@PathVariable Long id,@RequestBody UpdateAdvocateRequest updateAdvocateRequest){
        return new ResponseEntity<GenericResponseDto>(advocateService.updateAdvocate(id,updateAdvocateRequest), OK);
    }


    @PostMapping(path = "add_claimamt_advocate")
    public ResponseEntity<?> addClaimantAdvocate(@RequestBody CreateAdvocatePartyRequest createAdvocatePartyRequest){
        return new ResponseEntity<GenericResponseDto>(advocateService.assignAdvocateToClaimant(createAdvocatePartyRequest), OK);
    }

    @PostMapping(path = "add_respondant_advocate")
    public ResponseEntity<?> addRespondantAdvocate(@RequestBody CreateAdvocatePartyRequest createAdvocatePartyRequest){
        return new ResponseEntity<GenericResponseDto>(advocateService.assignAdvocateToRespondant(createAdvocatePartyRequest), OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllAdvocate(){
        return new ResponseEntity<List<AdvocateResponseDto>>(advocateService.getAllAdvocates(),OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmission(@PathVariable Long id){
        return new ResponseEntity<>(advocateService.deleteAdvocate(id), HttpStatus.OK);
    }


}
