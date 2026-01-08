package com.mac.arbitrator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "setting",uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "id")
})
public class Setting {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> emailSetting;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> schedularSetting;
}
