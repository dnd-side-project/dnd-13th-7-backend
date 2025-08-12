
ALTER TABLE `club_recruitment`
DROP COLUMN `recruitment_part`;

-- 모집 절차 정보 테이블
CREATE TABLE `process` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `process_description` VARCHAR(255),
    `sequence` INT,
    `club_id` BIGINT,
    CONSTRAINT `fk_process_to_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
);

-- 동아리 내 역할(포지션) 정보 테이블
CREATE TABLE `position` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `position_name` VARCHAR(255),
    `club_id` BIGINT,
    CONSTRAINT `fk_position_to_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
);

-- 모집 분야 테이블
CREATE TABLE `recruitment_part` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `part_name` VARCHAR(255),
    `club_recruitment_id` BIGINT,
    CONSTRAINT `fk_recruitment_part_to_club_recruitment` FOREIGN KEY (`club_recruitment_id`) REFERENCES `club_recruitment` (`club_id`) ON DELETE CASCADE
);

CREATE TABLE `target` (
    `id` BIGINT AUTO_INCREMENT,
    `target_name` VARCHAR(50) NOT NULL UNIQUE,
    `club_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_target_to_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
);
