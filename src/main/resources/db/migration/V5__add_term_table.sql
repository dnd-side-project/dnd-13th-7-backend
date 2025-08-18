CREATE TABLE `term` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `app_user_id` BIGINT NOT NULL,
    `over_age` TINYINT(1) NOT NULL,
    `term_of_service` TINYINT(1) NOT NULL,
    `privacy_policy` TINYINT(1) NOT NULL,
    `marketing_privacy` TINYINT(1) NOT NULL,
    `event_notification` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_term_app_user`
        FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE = InnoDB;
