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


CREATE TABLE `Club`
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
    `imageUrl`          VARCHAR(255)
) ENGINE = InnoDB;


CREATE TABLE `ClubRecruitment`
(
    `club_id`             BIGINT PRIMARY KEY,
    `recruitmentPart`     VARCHAR(255),
    `qualification`       VARCHAR(255),
    `recruitmentSchedule` VARCHAR(255),
    `activityPeriod`      VARCHAR(255),
    `activityMethod`      VARCHAR(255),
    `activityFee`         VARCHAR(255),
    `homepageUrl`         VARCHAR(255),
    `noticeUrl`           VARCHAR(255),
    FOREIGN KEY (`club_id`) REFERENCES `Club` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE `ClubActivity`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `club_id`          BIGINT,
    `hashtag`          VARCHAR(255),
    `activityName`     VARCHAR(255),
    `activityDescribe` TEXT,
    `imageUrl`         VARCHAR(255),
    `activityOrder`    INT,
    FOREIGN KEY (`club_id`) REFERENCES `Club` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;


CREATE TABLE `ClubSchedule`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `club_id`     BIGINT,
    `periodValue` INT,
    `period`      VARCHAR(255),
    `activity`    VARCHAR(255),
    FOREIGN KEY (`club_id`) REFERENCES `Club` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB;
