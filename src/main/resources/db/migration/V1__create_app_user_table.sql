CREATE TABLE `app_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(255) NULL,
    `provider` VARCHAR(50) NOT NULL,
    `active` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB