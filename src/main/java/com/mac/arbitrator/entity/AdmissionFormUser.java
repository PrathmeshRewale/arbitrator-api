package com.mac.arbitrator.entity;

import com.mac.arbitrator.entity.enums.UserCaseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admission_form_user",uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = {"admission_id","user_id"})
})
public class AdmissionFormUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long admissionId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserCaseType userCaseType;
}
