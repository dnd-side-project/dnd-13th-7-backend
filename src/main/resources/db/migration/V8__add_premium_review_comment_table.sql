CREATE TABLE `premium_review_comment`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT,
    `premium_review_id` bigint NOT NULL,
    `app_user_id` bigint NOT NULL,
    `content` TEXT NOT NULL,
    `parent_comment_id` bigint NULL,
    `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_premiumreviewcomment_on_premiumreview` FOREIGN KEY (`premium_review_id`) REFERENCES `premium_review` (`id`),
    CONSTRAINT `fk_premiumreviewcomment_on_appuser` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE = InnoDB;