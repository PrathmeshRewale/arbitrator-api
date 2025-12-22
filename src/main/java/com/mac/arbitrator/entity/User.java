package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.Gender;
import com.mac.arbitrator.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private Long roleId;

    private UserType userType;

    //    advocate details
    private Long jurisdictionId;
    private String jurisdictionName;
    @Column(name = "bar_registration_number", unique = true)
    private String barRegistrationNumber;
    @Column(name = "enrollment_date")
    private Instant enrollmentDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    //    advocate details
    private Instant createdAt;
    private Long createdById;
    private String createdByName;
    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true; }
}
