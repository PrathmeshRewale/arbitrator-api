package com.mac.arbitrator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "state",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long countryId;
    @Column(nullable = false)
    private String countryName;
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
