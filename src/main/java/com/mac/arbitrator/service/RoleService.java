package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateRoleRequest;
import com.mac.arbitrator.dto.request.update.UpdateRoleRequest;
import com.mac.arbitrator.dto.response.RoleResponseDto;

import java.util.List;

public interface RoleService {
    List<RoleResponseDto> getAllRoles();

    GenericResponseDto createRole(CreateRoleRequest createRoleRequest);

    RoleResponseDto getRoleById(Long id);

    GenericResponseDto deleteRole(Long id);

    GenericResponseDto updateRole(Long id, UpdateRoleRequest updateRoleRequest);
}
