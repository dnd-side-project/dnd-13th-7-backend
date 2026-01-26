CREATE TABLE tb_blog_review
(
    blog_review_id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '북마크 ID',
    club_id     BIGINT       NOT NULL COMMENT '동아리 ID',
    job_id      BIGINT       NULL COMMENT '직군 ID',
    title       VARCHAR(50)  NOT NULL COMMENT '제목',
    blog_name   VARCHAR(50)  NULL COMMENT '블로그 명',
    generation  INTEGER      NULL COMMENT '기수',
    blog_url    VARCHAR(255) NOT NULL COMMENT '블로그 주소',
    image_url   VARCHAR(255) NULL COMMENT '블로그 썸네일 이미지 URL',
    created_date  DATETIME(6)  NOT NULL COMMENT '생성 일시',
    updated_date  DATETIME(6)  NOT NULL COMMENT '수정 일시',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='블로그 리뷰';