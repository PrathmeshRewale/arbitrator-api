package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.update.UpdateRoleRequestDto;
import com.mac.arbitrator.entity.Role;

import java.util.List;

public interface RoleService {
    GenericResponseDto updatedRole(Long id, UpdateRoleRequestDto updateRoleRequest);
    Role getById(Long id);
    Role getRoleByName(String name);
    List<Role> getAllRole();
    void deleteById(Long id);
}
