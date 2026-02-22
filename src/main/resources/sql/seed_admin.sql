START TRANSACTION;

-- Users
INSERT INTO tb_user (
    name, email, nickname, profile_image_url, provider,
    active, job_id, subscription_email, email_notify_agree, status,
    deleted_date, deleted, created_date, updated_date, role
) VALUES (
    'Admin User', 'admin@moyeoit.com', 'admin', NULL, 'GOOGLE',
    1, NULL, NULL, 0, 'OFFICE_WORKER',
    NULL, 0, NOW(), NOW(), 'ADMIN'
);
SET @admin_id := LAST_INSERT_ID();

INSERT INTO tb_user (
    name, email, nickname, profile_image_url, provider,
    active, job_id, subscription_email, email_notify_agree, status,
    deleted_date, deleted, created_date, updated_date, role
) VALUES (
    'Demo User', 'demo@moyeoit.com', 'demo', NULL, 'KAKAO',
    1, NULL, NULL, 0, 'STUDENT',
    NULL, 0, NOW(), NOW(), 'USER'
);
SET @demo_id := LAST_INSERT_ID();

-- Categories
INSERT INTO tb_category (created_at, updated_at, name)
VALUES (NOW(), NOW(), '공지');
SET @cat_notice := LAST_INSERT_ID();

INSERT INTO tb_category (created_at, updated_at, name)
VALUES (NOW(), NOW(), '자유');
SET @cat_free := LAST_INSERT_ID();

-- Posts
INSERT INTO tb_post (
    comment_count, is_deleted, like_count, view_count,
    category_id, created_at, post_id, updated_at, user_id,
    title, content, post_type, status, reported_at, report_memo
) VALUES (
    0, 0, 3, 12,
    @cat_notice, NOW(), NULL, NOW(), @admin_id,
    '환영합니다', '어드민 테스트용 공지 글입니다.', 'GENERAL', 'ACTIVE', NULL, NULL
);
SET @post_notice := LAST_INSERT_ID();

INSERT INTO tb_post (
    comment_count, is_deleted, like_count, view_count,
    category_id, created_at, post_id, updated_at, user_id,
    title, content, post_type, status, reported_at, report_memo
) VALUES (
    2, 0, 1, 5,
    @cat_free, NOW(), NULL, NOW(), @demo_id,
    '신고 테스트 글', '신고 처리 테스트용 글입니다.', 'GENERAL', 'REPORTED', NOW(), '신고 메모 예시'
);
SET @post_reported := LAST_INSERT_ID();

INSERT INTO tb_post (
    comment_count, is_deleted, like_count, view_count,
    category_id, created_at, post_id, updated_at, user_id,
    title, content, post_type, status, reported_at, report_memo
) VALUES (
    0, 0, 0, 1,
    @cat_free, NOW(), NULL, NOW(), @demo_id,
    '블라인드 테스트 글', '블라인드 처리 테스트용 글입니다.', 'GENERAL', 'BLINDED', NOW(), '블라인드 사유'
);
SET @post_blinded := LAST_INSERT_ID();

-- Comments
INSERT INTO tb_comment (
    is_deleted, like_count, created_at, parent_comment_id, post_id,
    updated_at, user_id, content, status, reported_at, report_memo, deleted_content
) VALUES (
    0, 0, NOW(), NULL, @post_reported,
    NOW(), @demo_id, '신고 테스트 댓글입니다.', 'REPORTED', NOW(), '댓글 신고 메모', NULL
);

INSERT INTO tb_comment (
    is_deleted, like_count, created_at, parent_comment_id, post_id,
    updated_at, user_id, content, status, reported_at, report_memo, deleted_content
) VALUES (
    0, 0, NOW(), NULL, @post_notice,
    NOW(), @admin_id, '정상 댓글입니다.', 'ACTIVE', NULL, NULL, NULL
);

INSERT INTO tb_comment (
    is_deleted, like_count, created_at, parent_comment_id, post_id,
    updated_at, user_id, content, status, reported_at, report_memo, deleted_content
) VALUES (
    1, 0, NOW(), NULL, @post_notice,
    NOW(), @demo_id, '삭제된 댓글입니다.', 'DELETED', NULL, NULL, '삭제 전 원문 댓글'
);

-- Clubs
INSERT INTO tb_club (
    name, slogan, bio, establishment, total_participant, operation,
    image_url, online, offline, location, address, significant,
    recruiting, subscribe_count
) VALUES (
    '모여잇 개발 동아리', '함께 만드는 성장', '개발을 좋아하는 사람들의 모임입니다.',
    '2020-01-01', 30, 6,
    NULL, '온라인', '오프라인', '서울', '서울시 어딘가', '우수 동아리',
    1, 12
);

COMMIT;
