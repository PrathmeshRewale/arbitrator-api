package com.mac.arbitrator.controller;

import com.mac.arbitrator.dto.request.update.UpdateRoleRequestDto;
import com.mac.arbitrator.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id,
                                        @RequestBody UpdateRoleRequestDto updateRoleRequestDto) {
        return ResponseEntity.ok(roleService.updatedRole(id, updateRoleRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getRoleByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRole());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
