package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "jurisdictions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jurisdiction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
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
