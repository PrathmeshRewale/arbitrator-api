package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "arbitrator",uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
})
public class Arbitrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String barRegistrationNumber;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "jurisdiction_id", nullable = false)
    private String jurisdictionId;

    @Column(name = "jurisdiction_name", nullable = false)
    private String jurisdictionName;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Long createdById;

    @Column(nullable = false)
    private String createdByName;

    private Instant updatedAt;
    private Long updatedById;
    private String updatedByName;

}
