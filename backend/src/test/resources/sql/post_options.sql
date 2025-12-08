SET
FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE post_option;
SET
FOREIGN_KEY_CHECKS = 1;

INSERT INTO post_option (id, name, deposit, fee, post_id, created_at, modified_at)
VALUES (1, '헬멧 포함', 0, 0, 1, NOW(), NOW()),
       (2, '등산스틱 포함', 0, 0, 2, NOW(), NOW()),
       (3, '골프공 1세트', 0, 0, 3, NOW(), NOW()),
       (4, '충전기 포함', 0, 0, 4, NOW(), NOW()),
       (5, '배터리 2개', 0, 0, 5, NOW(), NOW()),
       (6, '태블릿 케이스 포함', 0, 0, 6, NOW(), NOW());
