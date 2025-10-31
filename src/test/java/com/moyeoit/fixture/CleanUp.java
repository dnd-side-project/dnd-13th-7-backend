package com.moyeoit.fixture;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CleanUp {

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    public CleanUp(JdbcTemplate jdbcTemplate,
                   EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @Transactional
    public void all() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        entityManager.getMetamodel().getEntities().stream()
                .map(entityType -> {
                    Table table = entityType.getJavaType().getAnnotation(Table.class);
                    return table != null && !table.name().isEmpty()
                            ? table.name()
                            : entityType.getName();
                })
                .forEach(tableName -> {
                    jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
                });

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

}
