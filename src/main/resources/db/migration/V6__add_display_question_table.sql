CREATE TABLE `display_question`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT,
    `question_id`     bigint       NOT NULL,
    `review_type`     varchar(255) NOT NULL,
    `review_category` varchar(255) NOT NULL,
    `sort`           integer      NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_displayquestion_on_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE = InnoDB;