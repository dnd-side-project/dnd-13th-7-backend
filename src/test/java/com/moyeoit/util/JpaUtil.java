package com.moyeoit.util;

import jakarta.persistence.EntityManager;

public final class JpaUtil {

    private JpaUtil() {
    }

    public static void persistAll(EntityManager em, Object... entities) {
        for (Object e : entities) {
            em.persist(e);
        }
    }
}
