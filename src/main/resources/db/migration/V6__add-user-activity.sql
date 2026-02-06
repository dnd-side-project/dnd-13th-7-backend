-- 유저 활동 테이블 추가
CREATE TABLE tb_user_activity (
    user_activity_id    BIGINT          NOT NULL AUTO_INCREMENT COMMENT '사용자 활동 식별자',
    user_id             BIGINT          NOT NULL COMMENT '유저 ID',
    club_id             BIGINT          NULL     COMMENT '모임(클럽) ID',
    job_id              BIGINT          NULL     COMMENT '직무/역할 ID',
    generation          INT             NULL     COMMENT '기수',
    activity_start_date DATETIME(6)     NULL     COMMENT '활동 시작일',
    activity_end_date   DATETIME(6)     NULL     COMMENT '활동 종료일',
    active              TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '현재 활성화 여부',
    certify             TINYINT(1)      NOT NULL DEFAULT 0 COMMENT '인증 여부',

    PRIMARY KEY (user_activity_id)
);

-- 유저 테이블 [구독 이메일, 이메일 수신 동의 여부] 컬럼 추가
ALTER TABLE tb_user
ADD COLUMN subscription_email VARCHAR(255) AFTER job_id,
ADD COLUMN email_notify_agree BOOLEAN NOT NULL DEFAULT FALSE AFTER subscription_email,
ADD COLUMN status VARCHAR(50) NULL AFTER email_notify_agree;

