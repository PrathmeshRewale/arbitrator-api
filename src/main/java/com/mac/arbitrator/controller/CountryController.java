package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateCountryRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateCountryRequestDto;
import com.mac.arbitrator.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCountry(@RequestBody CreateCountryRequestDto createCountryRequestDto){
        return ResponseEntity.ok(countryService.create(createCountryRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable Long id, @RequestBody UpdateCountryRequestDto updateCountryRequestDto){
        return ResponseEntity.ok(countryService.update(id,updateCountryRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id){
        return  ResponseEntity.ok(countryService.delete(id));
    }

    @GetMapping("/mini")
    public ResponseEntity<?> getAllCountryMini(){
        return ResponseEntity.ok(countryService.getAllCountryMini());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable Long id){
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCountry(){
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @PostMapping("/bulk_insert")
    public ResponseEntity<?> createMultipleCountrys(@RequestBody List<CreateCountryRequestDto> createCountryRequestDtos){
        return ResponseEntity.ok(countryService.bulkInsert(createCountryRequestDtos));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkName(@RequestParam String name){
        return ResponseEntity.ok(countryService.checkAvailability(name));
    }


}
