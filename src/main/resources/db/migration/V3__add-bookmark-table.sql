CREATE TABLE tb_bookmark
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '북마크 ID',
    user_id     BIGINT       NOT NULL COMMENT '유저 ID',
    target_id   BIGINT       NOT NULL COMMENT '북마크 대상 ID (동아리 ID 또는 리뷰 ID)',
    type        VARCHAR(50)  NOT NULL COMMENT '북마크 타입 (CLUB, INTERVIEW_REVIEW, ACTIVITY_REVIEW, BLOG_REVIEW)',
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE COMMENT '북마크 활성화 여부',
    created_at  DATETIME(6)  NOT NULL COMMENT '생성 일시',
    updated_at  DATETIME(6)  NOT NULL COMMENT '수정 일시',
    INDEX idx_bookmark_user_id (user_id),
    INDEX idx_bookmark_target_id_type (target_id, type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='북마크';
