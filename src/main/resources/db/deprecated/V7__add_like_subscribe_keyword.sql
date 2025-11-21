CREATE TABLE review_like (
    id BIGINT NOT NULL AUTO_INCREMENT,
    app_user_id BIGINT NOT NULL,
    review_id BIGINT NOT NULL,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    review_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(id)
);

CREATE TABLE `club_keyword` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `content` varchar(255) NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uk_content` (`content`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `club_club_keyword` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `club_id` bigint NOT NULL,
                                     `club_keyword_id` bigint NOT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_club_keyword_combination` (`club_id`, `club_keyword_id`), -- 클럽과 키워드 조합의 중복 방지
                                     KEY `fk_club_club_keyword_on_club` (`club_id`),
                                     KEY `fk_club_club_keyword_on_keyword` (`club_keyword_id`),
                                     CONSTRAINT `fk_club_club_keyword_on_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE,
                                     CONSTRAINT `fk_club_club_keyword_on_keyword` FOREIGN KEY (`club_keyword_id`) REFERENCES `club_keyword` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `club_subscribe` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint NOT NULL,
                               `club_id` bigint NOT NULL,
                               `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_user_club_follow` (`user_id`, `club_id`),
                               KEY `fk_club_subscribe_on_user` (`user_id`),
                               KEY `fk_club_subscribe_club` (`club_id`),
                               CONSTRAINT `fk_club_subscribe_on_user` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE CASCADE,
                               CONSTRAINT `fk_club_subscribe_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;