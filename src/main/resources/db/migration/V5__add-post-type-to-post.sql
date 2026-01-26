ALTER TABLE `tb_post`
    ADD COLUMN `post_type` varchar(20) NOT NULL DEFAULT 'GENERAL';

UPDATE `tb_post`
SET `post_type` = 'GENERAL'
WHERE `post_type` IS NULL;
