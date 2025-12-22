package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateUserRequest;
import com.mac.arbitrator.dto.request.update.UpdateUserPasswordRequest;
import com.mac.arbitrator.dto.request.update.UpdateUserRequest;
import com.mac.arbitrator.dto.response.UserResponseDto;
import com.mac.arbitrator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/apiv1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<List<UserResponseDto>>(userService.getAllUsers(), OK);
    }

    @PutMapping(path = "/update/{userId}")
    public ResponseEntity<?> updateUserWithUserId(@PathVariable Long userId,@RequestBody UpdateUserRequest updateUserRequest){
        return new ResponseEntity<GenericResponseDto>(userService.updateUserWithUserId(userId,updateUserRequest),OK);
    }

    @PutMapping(path = "/update_password/{userId}")
    public ResponseEntity<?> updateUserPasswordWithUserId(@PathVariable Long userId,@RequestBody UpdateUserPasswordRequest updateUserPasswordRequest){
        return new ResponseEntity<GenericResponseDto>(userService.updateUserPasswordWithUserId(userId,updateUserPasswordRequest),OK);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<GenericResponseDto>(userService.createUser(createUserRequest),OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmission(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }

}
