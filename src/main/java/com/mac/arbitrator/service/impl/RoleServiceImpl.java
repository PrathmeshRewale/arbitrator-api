package com.mac.arbitrator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateRoleRequest;
import com.mac.arbitrator.dto.request.update.UpdateRoleRequest;
import com.mac.arbitrator.dto.response.RoleResponseDto;
import com.mac.arbitrator.entity.Role;
import com.mac.arbitrator.repository.RoleRepository;
import com.mac.arbitrator.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<RoleResponseDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(this::mapToRoleResponse).collect(Collectors.toList());
    }

    @Override
    public GenericResponseDto createRole(CreateRoleRequest createRoleRequest) {
            if(createRoleRequest.permissions() == null){
                return new GenericResponseDto("error", "Permission Object is null");
            }
            Role role = new Role();
            role.setName(createRoleRequest.name());
            role.setDescription(createRoleRequest.description());
            role.setPermissions(mapPermissionMapToString(createRoleRequest.permissions()));
            role.setCreatedAt(Instant.now());
            role.setCreatedById(createRoleRequest.createdById());
            role.setCreatedByName(createRoleRequest.createdByName());
            roleRepository.saveAndFlush(role);
            return new GenericResponseDto("success", "Role Created Successfully");

    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow();
        return this.mapToRoleResponse(role);
    }

    @Override
    public GenericResponseDto deleteRole(Long id) {
        roleRepository.deleteById(id);
        return new GenericResponseDto("success", "Role Deleted Successfully");
    }

    @Override
    public GenericResponseDto updateRole(Long id, UpdateRoleRequest updateRoleRequest) {
            Role role = roleRepository.findById(id).orElseThrow();
            role.setName(updateRoleRequest.name());
            role.setDescription(updateRoleRequest.description());
            role.setPermissions(mapPermissionMapToString(updateRoleRequest.permissions()));
            role.setUpdatedAt(Instant.now());
            role.setUpdatedById(updateRoleRequest.updatedById());
            role.setUpdatedByName(updateRoleRequest.updatedByName());
            roleRepository.saveAndFlush(role);
            return new GenericResponseDto("success", "Role Updated Successfully");
    }

    public RoleResponseDto mapToRoleResponse(Role role){
        Map<String, Object> permissionMap = null;
        try{
            permissionMap = objectMapper.readValue(role.getPermissions(), Map.class);
        } catch (JsonMappingException e) {
//                throw new RuntimeException(e);
            permissionMap = Map.of("error", "invalid JSON");
        } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
            permissionMap = Map.of("error", "Invalid JSON");
        }

        return new RoleResponseDto(
                role.getId(),
                role.getName(),
                role.getDescription(),
                permissionMap,
                role.getCreatedAt(),
                role.getCreatedById(),
                role.getCreatedByName(),
                role.getUpdatedAt(),
                role.getUpdatedById(),
                role.getUpdatedByName()
        );
    }

    public Map<String,Object> mapPermissionStringToMap(String permissions){
        Map<String, Object> permissionMap = null;
        try{
            permissionMap = objectMapper.readValue(permissions, Map.class);
        } catch (JsonMappingException e) {
//                throw new RuntimeException(e);
            permissionMap = Map.of("error", "invalid JSON");
        } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
            permissionMap = Map.of("error", "Invalid JSON");
        }

       return permissionMap;
    }

    public String mapPermissionMapToString(Map<String, Object> map){
        try{
            return objectMapper.writeValueAsString(map);
        }catch (Exception exception){
            System.out.println(exception);
            throw new IllegalArgumentException(exception);
        }
    }
}
