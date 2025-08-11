CREATE TABLE `app_user`
(
    `id`       BIGINT       NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `email`    VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(255) NULL,
    `provider` VARCHAR(50)  NOT NULL,
    `active`   TINYINT(1)   NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;


CREATE TABLE `club`
(
    `id`                BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`              VARCHAR(255),
    `position`          VARCHAR(255),
    `slogan`            VARCHAR(255),
    `bio`               TEXT,
    `establishment`     INT,
    `total_participant` INT,
    `operation`         INT,
    `offline`           VARCHAR(255),
    `online`            VARCHAR(255),
    `location`          VARCHAR(255),
    `address`           VARCHAR(255),
    `recruiting`        BOOLEAN,
    `image_url`          VARCHAR(255)
) ENGINE = InnoDB;


CREATE TABLE `club_recruitment`
(
    `club_id`             BIGINT PRIMARY KEY,
    `recruitment_part`     VARCHAR(255),
    `qualification`       VARCHAR(255),
    `recruitment_schedule` VARCHAR(255),
    `activity_period`      VARCHAR(255),
    `activity_method`      VARCHAR(255),
    `activity_fee`         VARCHAR(255),
    `homepage_url`         VARCHAR(255),
    `notice_url`           VARCHAR(255),
    FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE `club_activity`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `club_id`          BIGINT,
    `hashtag`          VARCHAR(255),
    `activity_name`     VARCHAR(255),
    `activity_describe` TEXT,
    `image_url`         VARCHAR(255),
    `activity_order`    INT,
    FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE `club_schedule`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `club_id`     BIGINT,
    `period_value` INT,
    `period`      VARCHAR(255),
    `activity`    VARCHAR(255),
    FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;

ALTER TABLE `club` ADD COLUMN `process` JSON;
ALTER TABLE `club`
    ADD COLUMN `target_university` TINYINT(1) NOT NULL DEFAULT 0 AFTER `process`,
    ADD COLUMN `target_worker` TINYINT(1) NOT NULL DEFAULT 0 AFTER `target_university`;

CREATE TABLE `recruitment_part` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `part_name` VARCHAR(255),
    `club_recruitment_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_recruitment_part_club_recruitment`
    FOREIGN KEY (`club_recruitment_id`) REFERENCES `club_recruitment` (`club_id`)
);
