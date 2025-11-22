CREATE TABLE `tb_category`
(
    `category_id` bigint      NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) DEFAULT NULL,
    `updated_at`  datetime(6) DEFAULT NULL,
    `name`        varchar(50) NOT NULL,
    PRIMARY KEY (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `tb_comment`
(
    `comment_id`        bigint NOT NULL AUTO_INCREMENT,
    `is_deleted`        tinyint(1)      DEFAULT NULL,
    `like_count`        int         DEFAULT NULL,
    `created_at`        datetime(6) DEFAULT NULL,
    `parent_comment_id` bigint      DEFAULT NULL,
    `post_id`           bigint      DEFAULT NULL,
    `updated_at`        datetime(6) DEFAULT NULL,
    `user_id`           bigint NOT NULL,
    `content`           text   NOT NULL,
    PRIMARY KEY (`comment_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `tb_post`
(
    `comment_count` int         DEFAULT NULL,
    `is_deleted`    tinyint(1)      DEFAULT NULL,
    `like_count`    int         DEFAULT NULL,
    `view_count`    int         DEFAULT NULL,
    `category_id`   bigint      DEFAULT NULL,
    `created_at`    datetime(6) DEFAULT NULL,
    `post_id`       bigint       NOT NULL AUTO_INCREMENT,
    `updated_at`    datetime(6) DEFAULT NULL,
    `user_id`       bigint       NOT NULL,
    `title`         varchar(100) NOT NULL,
    `content`       text         NOT NULL,
    PRIMARY KEY (`post_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `tb_post_image`
(
    `is_representative` tinyint(1)       NOT NULL,
    `order_index`       int         DEFAULT NULL,
    `created_at`        datetime(6) DEFAULT NULL,
    `image_id`          bigint       NOT NULL AUTO_INCREMENT,
    `post_id`           bigint      DEFAULT NULL,
    `image_url`         varchar(255) NOT NULL,
    PRIMARY KEY (`image_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `tb_post_likes`
(
    `created_at`  datetime(6) DEFAULT NULL,
    `like_id`     bigint                  NOT NULL AUTO_INCREMENT,
    `target_id`   bigint                  NOT NULL,
    `user_id`     bigint                  NOT NULL,
    `target_type` enum ('COMMENT','POST') NOT NULL,
    PRIMARY KEY (`like_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

