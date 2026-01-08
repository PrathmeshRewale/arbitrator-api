package com.mac.arbitrator.annotation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;


@Service
public class AutoCodeGeneratorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String generateCode(String prefix, String sequenceName) {

        entityManager.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS sequences (" +
                        "  name VARCHAR(100) PRIMARY KEY," +
                        "  next_val BIGINT NOT NULL" +
                        ")"
        ).executeUpdate();

        int inserted = entityManager.createNativeQuery(
                "INSERT IGNORE INTO sequences(name, next_val) VALUES(:name, 1)"
        ).setParameter("name", sequenceName).executeUpdate();

        entityManager.createNativeQuery(
                        "UPDATE sequences SET next_val = LAST_INSERT_ID(next_val + 1) WHERE name = :name"
                )
                .setParameter("name", sequenceName)
                .executeUpdate();

        Long nextVal = ((Number) entityManager
                .createNativeQuery("SELECT LAST_INSERT_ID()")
                .getSingleResult())
                .longValue();


        int year = Year.now().getValue();
        return prefix + "/" + nextVal + "/" + year;
    }
}
