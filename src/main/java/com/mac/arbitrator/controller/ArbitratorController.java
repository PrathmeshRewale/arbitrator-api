package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateArbitratorRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateArbitratorPasswordRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateArbitratorRequestDto;
import com.mac.arbitrator.dto.response.ArbitratorResponseDto;
import com.mac.arbitrator.service.ArbitratorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/arbitrator")
public class ArbitratorController {

    private final ArbitratorService arbitratorService;

    public ArbitratorController(ArbitratorService arbitratorService) {
        this.arbitratorService = arbitratorService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArbitrator(
            @RequestBody CreateArbitratorRequestDto createArbitratorRequestDto) {
        return ResponseEntity.ok(arbitratorService.create(createArbitratorRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArbitrator(
            @PathVariable Long id,
            @RequestBody UpdateArbitratorRequestDto updateArbitratorRequestDto) {
        return ResponseEntity.ok(arbitratorService.update(id, updateArbitratorRequestDto));
    }

    @PutMapping("/update/password")
    public ResponseEntity<?> updateArbitrator(
            @RequestBody UpdateArbitratorPasswordRequestDto updateArbitratorPasswordRequestDto) {
        return ResponseEntity.ok(arbitratorService.resetArbitratorPassword(updateArbitratorPasswordRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArbitrator(@PathVariable Long id) {
        return ResponseEntity.ok(arbitratorService.delete(id));
    }

    @PostMapping("/bulk-insert")
    public ResponseEntity<?> bulkInsert(
            @RequestBody List<CreateArbitratorRequestDto> createArbitratorRequestDtos) {
        return ResponseEntity.ok(arbitratorService.bulkInsert(createArbitratorRequestDtos));
    }

    @GetMapping
    public Page<ArbitratorResponseDto> getAllArbitrators(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return arbitratorService.findAll(pageable);
    }
}
