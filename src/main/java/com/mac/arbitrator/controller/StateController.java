package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateStateRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateStateRequestDto;
import com.mac.arbitrator.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createState(@RequestBody CreateStateRequestDto createStateRequestDto){
        return ResponseEntity.ok(stateService.create(createStateRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateState(@PathVariable Long id, @RequestBody UpdateStateRequestDto updateStateRequestDto){
        return ResponseEntity.ok(stateService.update(id,updateStateRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteState(@PathVariable Long id){
        return  ResponseEntity.ok(stateService.delete(id));
    }

    @GetMapping("/mini")
    public ResponseEntity<?> getAllStateMini(){
        return ResponseEntity.ok(stateService.getAllStateMini());
    }

    @GetMapping("/mini/{countryId}")
    public ResponseEntity<?> getAllStateMiniByCountryId(@PathVariable Long countryId){
        return ResponseEntity.ok(stateService.getAllStateMiniByCountryId(countryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStateById(@PathVariable Long id){
        return ResponseEntity.ok(stateService.getStateById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllState(){
        return ResponseEntity.ok(stateService.getAllState());
    }

    @PostMapping("/bulk_insert")
    public ResponseEntity<?> createMultipleStates(@RequestBody List<CreateStateRequestDto> createStateRequestDtos){
        return ResponseEntity.ok(stateService.bulkInsert(createStateRequestDtos));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkName(@RequestParam String name){
        return ResponseEntity.ok(stateService.checkAvailability(name));
    }


}
