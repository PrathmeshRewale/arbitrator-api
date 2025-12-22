package com.mac.arbitrator.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac.arbitrator.entity.Role;
import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.repository.RoleRepository;
import com.mac.arbitrator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("ADMIN", "Full admin access", DataInitializer.getAdminPermissions());
        createRoleIfNotExists("USER", "Claimant limited access", DataInitializer.getUserPermission());
        createRoleIfNotExists("STAGNO", "Stagno limited access", DataInitializer.getStagnoPermission());
        createRoleIfNotExists("ARBITRATOR","Arbitrator limited access",DataInitializer.getArbitratorPermission());

        createAdminUserIfNotExists();
    }

    private void createAdminUserIfNotExists() {
        String username = "admin";
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            // Fetch the ADMIN role
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            // Create the admin user
            User admin = User.builder()
                    .username("admin") // or use email
                    .password(passwordEncoder.encode("admin123")) // Default password
                    .email("admin@gmail.com")
                    .fullName("System Administrator")
                    .phoneNo("9999999999")
                    .roleId(adminRole.getId())
                    .build();

            userRepository.save(admin);
            System.out.println("✅ Admin user created with email: " + "admin@gmail.com");
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }


    private void createRoleIfNotExists(String roleName, String description, Map<String, Map<String, String>> permissions) throws JsonProcessingException {
        Optional<Role> roleOpt = roleRepository.findByName(roleName);
        if (roleOpt.isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            role.setDescription(description);
            role.setPermissions(objectMapper.writeValueAsString(permissions));
            role.setCreatedAt(Instant.now());
            role.setCreatedById(1l);
            role.setCreatedByName("SYSTEM");
            roleRepository.save(role);
            System.out.println("✅ Created role: " + roleName);
        } else {
            System.out.println("ℹ️ Role already exists: " + roleName);
        }
    }

    public static Map<String, String> fullAccess() {
        return Map.of(
                "read", "yes",
                "create", "yes",
                "menu", "yes",
                "update", "yes",
                "delete", "yes"
        );
    }

    public static Map<String, Map<String, String>> getAdminPermissions() {
        return Map.of(
                "advocates", fullAccess(),
                "admissions", fullAccess(),
                "cases", fullAccess(),
                "jurisdictions",fullAccess(),
                "dashboard", fullAccess(),
                "roles", fullAccess(),
                "users", fullAccess(),
                "settings",fullAccess(),
                "payments",fullAccess()
        );
    }
    public static Map<String, Map<String, String>> getUserPermission() {
        return Map.of(
                "cases", fullAccess(),
                "dashboard", fullAccess()
        );
    }

    public static Map<String, Map<String, String>> getArbitratorPermission() {
        return Map.of(
                "cases", fullAccess(),
                "dashboard", fullAccess()
        );
    }

    public static Map<String, Map<String, String>> getStagnoPermission() {
        return Map.of(
                "cases", fullAccess(),
                "dashboard", fullAccess()
        );
    }


}
