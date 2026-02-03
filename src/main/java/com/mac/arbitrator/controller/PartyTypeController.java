package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreatePartyTypeRequestDto;
import com.mac.arbitrator.dto.request.update.UpdatePartyTypeRequestDto;
import com.mac.arbitrator.service.PartyTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/partytype")
public class PartyTypeController {

    private final PartyTypeService partyTypeService;

    public PartyTypeController(PartyTypeService partyTypeService) {
        this.partyTypeService = partyTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPartyType(@RequestBody CreatePartyTypeRequestDto createPartyTypeRequestDto){
        return ResponseEntity.ok(partyTypeService.create(createPartyTypeRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePartyType(@PathVariable Long id, @RequestBody UpdatePartyTypeRequestDto updatePartyTypeRequestDto){
        return ResponseEntity.ok(partyTypeService.update(id,updatePartyTypeRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePartyType(@PathVariable Long id){
        return  ResponseEntity.ok(partyTypeService.delete(id));
    }

    @GetMapping("/mini")
    public ResponseEntity<?> getAllPartyTypeMini(){
        return ResponseEntity.ok(partyTypeService.getAllPartyTypeMini());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPartyTypeById(@PathVariable Long id){
        return ResponseEntity.ok(partyTypeService.getPartyTypeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPartyType(){
        return ResponseEntity.ok(partyTypeService.getAllPartyType());
    }

    @PostMapping("/bulk_insert")
    public ResponseEntity<?> createMultiplePartyTypes(@RequestBody List<CreatePartyTypeRequestDto> createPartyTypeRequestDtos){
        return ResponseEntity.ok(partyTypeService.bulkInsert(createPartyTypeRequestDtos));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkName(@RequestParam String name){
        return ResponseEntity.ok(partyTypeService.checkAvailability(name));
    }


}
