-- club 테이블
ALTER TABLE `club`
    ADD COLUMN `subscribe_count` INT DEFAULT 0;

-- basic_review 테이블
ALTER TABLE `basic_review`
    ADD COLUMN `like_count` INT DEFAULT 0;

-- premium_review 테이블
ALTER TABLE `premium_review`
    ADD COLUMN `like_count` INT DEFAULT 0;