CREATE TABLE tb_job (
    job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    eng_name VARCHAR(255) NOT NULL
);

CREATE TABLE tb_term (
    term_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    over_age BOOLEAN DEFAULT FALSE,
    term_of_service BOOLEAN DEFAULT FALSE,
    privacy_policy BOOLEAN DEFAULT FALSE,
    marketing_privacy BOOLEAN DEFAULT FALSE,
    event_notification BOOLEAN DEFAULT FALSE
);

CREATE TABLE tb_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    profile_image_url VARCHAR(255),
    provider VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    job_id BIGINT,
    deleted_date DATETIME,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_club (
    club_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slogan VARCHAR(255),
    bio VARCHAR(255),
    establishment DATE,
    total_participant INTEGER,
    operation INTEGER,
    image_url VARCHAR(255),
    online VARCHAR(255),
    offline VARCHAR(255),
    location VARCHAR(255),
    address VARCHAR(255),
    significant VARCHAR(255),
    recruiting BOOLEAN NOT NULL DEFAULT FALSE,
    subscribe_count INTEGER
);

CREATE TABLE tb_club_activity (
    club_activity_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    name VARCHAR(255),
    hashtag VARCHAR(255),
    description VARCHAR(255),
    image_url VARCHAR(255),
    sequence INTEGER
);

CREATE TABLE tb_club_position (
    club_position_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE tb_club_process (
    club_process_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    description VARCHAR(255),
    sequence INTEGER
);

CREATE TABLE tb_club_schedule (
    club_schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    period_value INTEGER,
    period_type VARCHAR(255),
    activity VARCHAR(255)
);

CREATE TABLE tb_club_subscribe (
    club_subscribe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    club_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_club_recruitment (
    club_recruitment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id BIGINT NOT NULL,
    activity_period VARCHAR(255),
    recruitment_schedule VARCHAR(255),
    qualification VARCHAR(255),
    activity_method VARCHAR(255),
    activity_fee BIGINT,
    homepage_url VARCHAR(255),
    notice_url VARCHAR(255)
);

CREATE TABLE tb_club_recruitment_part (
    club_recruitment_part_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_recruitment_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL
);

CREATE TABLE tb_target (
    target_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_name VARCHAR(255),
    club_id BIGINT NOT NULL
);

CREATE TABLE tb_review (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    rate DOUBLE NOT NULL,
    result VARCHAR(255) NOT NULL,
    generation INTEGER NOT NULL,
    review_element_category VARCHAR(255) NOT NULL,
    club_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    like_count BIGINT NOT NULL DEFAULT 0,
    comment_count BIGINT NOT NULL DEFAULT 0,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_date DATETIME,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_review_comment (
    review_comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT NOT NULL,
    parent_id BIGINT,
    content VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_date DATETIME,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_review_answer (
    review_answer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT NOT NULL,
    review_question_id BIGINT NOT NULL,
    value VARCHAR(255),
    value_type VARCHAR(255),
    numeric_value DOUBLE,
    sequence INTEGER,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_review_question (
    review_question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    type VARCHAR(255) NOT NULL,
    limit_value BIGINT,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_review_option (
    review_option_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_question_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    sequence INTEGER,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_review_like (
    review_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    review_id BIGINT NOT NULL,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_review_like_user_review UNIQUE (user_id, review_id)
);

CREATE TABLE tb_review_content_summary (
    review_content_summray_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT NOT NULL,
    choice_summary VARCHAR(255),
    subjective_summary VARCHAR(255)
);
