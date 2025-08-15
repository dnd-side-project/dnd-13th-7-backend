CREATE TABLE `job`
(
    `id`       BIGINT       NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `eng_name`    VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

ALTER TABLE `app_user`
    ADD COLUMN `job_id` BIGINT NULL;


ALTER TABLE `app_user`
    ADD CONSTRAINT `fk_appuser_on_job`
        FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);
