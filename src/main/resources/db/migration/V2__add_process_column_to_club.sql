-- 기존 컬럼 삭제
ALTER TABLE `club_recruitment`
    DROP COLUMN `recruitment_part`;

ALTER TABLE `club`
    DROP COLUMN `position`;

CREATE TABLE `process` (
                           `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `process_description` VARCHAR(255),
                           `sequence` INT,
                           `club_id` BIGINT,
                           CONSTRAINT `fk_process_to_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
);

CREATE TABLE `position` (
                            `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                            `position_name` VARCHAR(255),
                            `club_id` BIGINT,
                            CONSTRAINT `fk_position_to_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
);

CREATE TABLE `recruitment_part` (
                                    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    `part_name` VARCHAR(255),
                                    `club_recruitment_id` BIGINT,
                                    CONSTRAINT `fk_recruitment_part_to_club_recruitment`
                                        -- 참조하는 컬럼을 올바르게 'club_id'로 변경
                                        FOREIGN KEY (`club_recruitment_id`) REFERENCES `club_recruitment` (`club_id`) ON DELETE CASCADE
);

CREATE TABLE `target` (
                          `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                          `target_name` VARCHAR(50) NOT NULL,
                          `club_id` BIGINT,
                          CONSTRAINT `FK_target_to_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE
);