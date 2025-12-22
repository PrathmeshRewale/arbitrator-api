package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateRoleRequest;
import com.mac.arbitrator.dto.request.update.UpdateRoleRequest;
import com.mac.arbitrator.dto.response.RoleResponseDto;
import com.mac.arbitrator.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/apiv1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public ResponseEntity<?> getAllRoles(){
        return new ResponseEntity<List<RoleResponseDto>>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id){
        return new ResponseEntity<RoleResponseDto>(roleService.getRoleById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createRole(@RequestBody CreateRoleRequest createRoleRequest){
        return new ResponseEntity<GenericResponseDto>(roleService.createRole(createRoleRequest), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody UpdateRoleRequest updateRoleRequest){
        return new ResponseEntity<GenericResponseDto>(roleService.updateRole(id, updateRoleRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
        return new ResponseEntity<GenericResponseDto>(roleService.deleteRole(id), HttpStatus.OK);
    }

}

