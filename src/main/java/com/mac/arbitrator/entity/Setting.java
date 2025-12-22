package com.mac.arbitrator.entity;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "setting")
public class Setting {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String schedularSetting;

    @Lob
    @Column(nullable = false,columnDefinition = "LONGTEXT")
    private String emailSetting;

}
