package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.create.CreateUserRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateUserPasswordRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateUserRequestDto;
import com.mac.arbitrator.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequestDto createUserRequestDto) {
        return ResponseEntity.ok(userService.create(createUserRequestDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        return ResponseEntity.ok(userService.update(id, updateUserRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @PutMapping("/update_password")
    public ResponseEntity<?> updateUserPassword(
            @RequestBody UpdateUserPasswordRequestDto updateUserPasswordRequestDto) {
        return ResponseEntity.ok(userService.updateUserPassword(updateUserPasswordRequestDto));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAvailability(@RequestParam String name) {
        return ResponseEntity.ok(userService.checkAvailability(name));
    }
}
