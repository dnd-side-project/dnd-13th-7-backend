
CREATE TABLE `question` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            `title` VARCHAR(255),
                            `subtitle` VARCHAR(255),
                            `type` VARCHAR(255) NOT NULL -- QuestionType Enum: SUBJECTIVE, MULTIPLE_CHOICE
) ENGINE=InnoDB;


CREATE TABLE `question_element` (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    `question_id` BIGINT,
                                    `element_title` VARCHAR(255),
                                    `sequence` INT,
                                    CONSTRAINT `fk_questionelement_on_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `basic_review` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                `club_id` BIGINT,
                                `user_id` BIGINT,
                                `job_id` BIGINT,
                                `cohort` INT,
                                `rate` DOUBLE,
                                CONSTRAINT `fk_basicreview_on_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`),
                                CONSTRAINT `fk_basicreview_on_appuser` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
                                CONSTRAINT `fk_basicreview_on_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `premium_review` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  `club_id` BIGINT,
                                  `user_id` BIGINT,
                                  `job_id` BIGINT,
                                  `cohort` INT,
                                  `image_url` VARCHAR(255),
                                  `title` VARCHAR(255),
                                  CONSTRAINT `fk_premiumreview_on_club` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`),
                                  CONSTRAINT `fk_premiumreview_on_appuser` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
                                  CONSTRAINT `fk_premiumreview_on_job` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `basic_review_detail` (
                                       `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       `review_basic_id` BIGINT,
                                       `question_id` BIGINT,
                                       `user_id` BIGINT,
                                       `value` LONGTEXT, -- @Lob
                                       `answer_type` VARCHAR(255), -- AnswerType Enum: INTEGER, DOUBLE, TEXT
                                       CONSTRAINT `fk_basicreviewdetail_on_basicreview` FOREIGN KEY (`review_basic_id`) REFERENCES `basic_review` (`id`),
                                       CONSTRAINT `fk_basicreviewdetail_on_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
                                       CONSTRAINT `fk_basicreviewdetail_on_appuser` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `premium_review_detail` (
                                         `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         `review_premium_id` BIGINT,
                                         `question_id` BIGINT,
                                         `user_id` BIGINT,
                                         `value` LONGTEXT, -- @Lob
                                         `answer_type` VARCHAR(255), -- AnswerType Enum: INTEGER, DOUBLE, TEXT
                                         CONSTRAINT `fk_premiumreviewdetail_on_premiumreview` FOREIGN KEY (`review_premium_id`) REFERENCES `premium_review` (`id`),
                                         CONSTRAINT `fk_premiumreviewdetail_on_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
                                         CONSTRAINT `fk_premiumreviewdetail_on_appuser` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB;

ALTER TABLE `recruitment_part` DROP COLUMN `part_name`;


ALTER TABLE `recruitment_part` ADD COLUMN `job_id` BIGINT NULL;


ALTER TABLE `recruitment_part`
    ADD CONSTRAINT `fk_recruitmentpart_on_job`
        FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);

-- BASIC_REVIEW 수정
ALTER TABLE `basic_review`
    ADD COLUMN `result` ENUM('합격', '불합격') DEFAULT NULL,
    ADD COLUMN `review_type` ENUM('활동', '면접', '서류') DEFAULT NULL ,
    ADD COLUMN `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `update_date` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- PREMIUM_REVIEW 수정
ALTER TABLE `premium_review`
    ADD COLUMN `result` ENUM('합격', '불합격') DEFAULT NULL,
    ADD COLUMN `review_type` ENUM('활동', '면접', '서류') DEFAULT NULL,
    ADD COLUMN `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `update_date` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;