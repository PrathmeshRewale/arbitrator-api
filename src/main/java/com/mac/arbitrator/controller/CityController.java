package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateCityRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateCityRequestDto;
import com.mac.arbitrator.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCity(@RequestBody CreateCityRequestDto createCityRequestDto){
        return ResponseEntity.ok(cityService.create(createCityRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCity(@PathVariable Long id, @RequestBody UpdateCityRequestDto updateCityRequestDto){
        return ResponseEntity.ok(cityService.update(id,updateCityRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id){
        return  ResponseEntity.ok(id);
    }

    @GetMapping("/mini")
    public ResponseEntity<?> getAllCityMini(){
        return ResponseEntity.ok(cityService.getAllCityMini());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCity(){
        return ResponseEntity.ok(cityService.getAllCity());
    }

    @PostMapping("/bulk_insert")
    public ResponseEntity<?> createMultipleCitys(@RequestBody List<CreateCityRequestDto> createCityRequestDtos){
        return ResponseEntity.ok(cityService.bulkInsert(createCityRequestDtos));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkName(@RequestParam String name){
        return ResponseEntity.ok(cityService.checkAvailability(name));
    }


}
