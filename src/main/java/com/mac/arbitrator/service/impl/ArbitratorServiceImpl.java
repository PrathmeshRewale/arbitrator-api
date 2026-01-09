package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateArbitratorRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateArbitratorRequestDto;
import com.mac.arbitrator.dto.response.ArbitratorResponseDto;
import com.mac.arbitrator.entity.Arbitrator;
import com.mac.arbitrator.entity.ArbitratorUser;
import com.mac.arbitrator.entity.Role;
import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.repository.ArbitratorRepository;
import com.mac.arbitrator.repository.ArbitratorUserRepository;
import com.mac.arbitrator.repository.UserRepository;
import com.mac.arbitrator.service.ArbitratorService;
import com.mac.arbitrator.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ArbitratorServiceImpl implements ArbitratorService {

    private final ArbitratorRepository arbitratorRepository;
    private final UserRepository userRepository;
    private final ArbitratorUserRepository arbitratorUserRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public ArbitratorServiceImpl(ArbitratorRepository arbitratorRepository, UserRepository userRepository, ArbitratorUserRepository arbitratorUserRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.arbitratorRepository = arbitratorRepository;
        this.userRepository = userRepository;
        this.arbitratorUserRepository = arbitratorUserRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public GenericResponseDto create(CreateArbitratorRequestDto req) {

        Arbitrator arbitrator = new Arbitrator();
        arbitrator.setEmail(req.getEmail());
        arbitrator.setGender(req.getGender());
        arbitrator.setFullName(req.getFullName());
        arbitrator.setJurisdictionId(req.getJurisdictionId());
        arbitrator.setJurisdictionName(req.getJurisdictionName());
        arbitrator.setPhoneNumber(req.getPhoneNumber());
        arbitrator.setBarRegistrationNumber(req.getBarRegistrationNumber());
        arbitrator.setEnrollmentDate(req.getEnrollmentDate());
        arbitrator.setCreatedByName(req.getCreatedByName());
        arbitrator.setCreatedById(req.getCreatedById());
        arbitrator.setCreatedAt(Instant.now());

        Arbitrator arbitrator1 = arbitratorRepository.save(arbitrator);

        Role role = roleService.getRoleByName("ARBITRATOR");


        User user = new User();
        user.setPassword(passwordEncoder.encode("1234"));
        user.setUsername(arbitrator1.getEmail().split("@")[0]);
        user.setFullName(arbitrator1.getFullName());
        user.setRoleId(role.getId());
        user.setRoleName(role.getName());
        user.setPhoneNo(arbitrator1.getPhoneNumber());
        user.setEmail(arbitrator1.getEmail());

        User user1 = userRepository.save(user);

        ArbitratorUser arbitratorUser = new ArbitratorUser();
        arbitratorUser.setArbitratorId(arbitrator1.getId());
        arbitratorUser.setUserId(user1.getId());

        arbitratorUserRepository.save(arbitratorUser);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id, UpdateArbitratorRequestDto req) {

        Arbitrator arbitrator = arbitratorRepository.findById(id).orElseThrow(()->new RuntimeException("Arbitrator with id -> " + id + " not found"));
        arbitrator.setEmail(req.getEmail());
        arbitrator.setGender(req.getGender());
        arbitrator.setFullName(req.getFullName());
        arbitrator.setJurisdictionId(req.getJurisdictionId());
        arbitrator.setJurisdictionName(req.getJurisdictionName());
        arbitrator.setPhoneNumber(req.getPhoneNumber());
        arbitrator.setBarRegistrationNumber(req.getBarRegistrationNumber());
        arbitrator.setEnrollmentDate(req.getEnrollmentDate());
        arbitrator.setUpdatedByName(req.getUpdatedByName());
        arbitrator.setUpdatedById(req.getUpdatedById());
        arbitrator.setUpdatedAt(Instant.now());

        Arbitrator arbitrator1 = arbitratorRepository.save(arbitrator);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public GenericResponseDto delete(Long id) {
        arbitratorRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public GenericResponseDto bulkInsert(List<CreateArbitratorRequestDto> createArbitratorRequestDtos) {

        createArbitratorRequestDtos.forEach(req->{
            Arbitrator arbitrator = new Arbitrator();
            arbitrator.setEmail(req.getEmail());
            arbitrator.setGender(req.getGender());
            arbitrator.setFullName(req.getFullName());
            arbitrator.setJurisdictionId(req.getJurisdictionId());
            arbitrator.setJurisdictionName(req.getJurisdictionName());
            arbitrator.setPhoneNumber(req.getPhoneNumber());
            arbitrator.setBarRegistrationNumber(req.getBarRegistrationNumber());
            arbitrator.setEnrollmentDate(req.getEnrollmentDate());
            arbitrator.setCreatedByName(req.getCreatedByName());
            arbitrator.setCreatedById(req.getCreatedById());
            arbitrator.setCreatedAt(Instant.now());

            Arbitrator arbitrator1 = arbitratorRepository.save(arbitrator);

            Role role = roleService.getRoleByName("ARBITRATOR");


            User user = new User();
            user.setPassword(passwordEncoder.encode("1234"));
            user.setUsername(arbitrator1.getEmail().split("@")[0]);
            user.setFullName(arbitrator1.getFullName());
            user.setRoleId(role.getId());
            user.setRoleName(role.getName());
            user.setPhoneNo(arbitrator1.getPhoneNumber());
            user.setEmail(arbitrator1.getEmail());

            User user1 = userRepository.save(user);

            ArbitratorUser arbitratorUser = new ArbitratorUser();
            arbitratorUser.setArbitratorId(arbitrator1.getId());
            arbitratorUser.setUserId(user1.getId());

            arbitratorUserRepository.save(arbitratorUser);
        });

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public Page<ArbitratorResponseDto> findAll(Pageable pageable) {
        return arbitratorRepository.findAll(pageable)
                .map(this::mapToResponseDto);
    }

    private ArbitratorResponseDto mapToResponseDto(Arbitrator arbitrator) {
        return new ArbitratorResponseDto(
                arbitrator.getId(),
                arbitrator.getFullName(),
                arbitrator.getBarRegistrationNumber(),
                arbitrator.getEmail(),
                arbitrator.getGender(),
                arbitrator.getPhoneNumber(),
                arbitrator.getEnrollmentDate(),
                arbitrator.getJurisdictionId(),
                arbitrator.getJurisdictionName(),
                arbitrator.getCreatedAt(),
                arbitrator.getCreatedById(),
                arbitrator.getCreatedByName(),
                arbitrator.getUpdatedAt(),
                arbitrator.getUpdatedById(),
                arbitrator.getUpdatedByName()
        );
    }
}
