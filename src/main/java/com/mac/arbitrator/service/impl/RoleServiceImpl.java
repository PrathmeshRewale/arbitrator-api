package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.update.UpdateRoleRequestDto;
import com.mac.arbitrator.entity.Role;
import com.mac.arbitrator.repository.RoleRepository;
import com.mac.arbitrator.service.RoleService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public GenericResponseDto updatedRole(Long id, UpdateRoleRequestDto req) {

        Role role = getById(id);

        role.setUpdatedAt(Instant.now());
        role.setUpdatedById(req.getUpdatedById());
        role.setUpdatedByName(req.getUpdatedByName());

        role.setDescription(req.getDescription());
        role.setPermissions(req.getPermissions());

        return roleRepository.save(role).getId() != null ? new GenericResponseDto("success","role updated successfully") : new GenericResponseDto("error","Something went wrong");

    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Role with id -> " + id + " not found"));
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
