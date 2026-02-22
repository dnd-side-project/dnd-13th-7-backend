ALTER TABLE tb_user
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';

ALTER TABLE tb_post
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN reported_at DATETIME NULL,
    ADD COLUMN report_memo VARCHAR(255) NULL;

UPDATE tb_post
SET status = 'DELETED'
WHERE is_deleted = 1;

ALTER TABLE tb_comment
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN reported_at DATETIME NULL,
    ADD COLUMN report_memo VARCHAR(255) NULL,
    ADD COLUMN deleted_content TEXT NULL;

UPDATE tb_comment
SET status = 'DELETED'
WHERE is_deleted = 1;
