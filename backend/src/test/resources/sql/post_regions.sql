SET
FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE post_region;
SET
FOREIGN_KEY_CHECKS = 1;

INSERT INTO post_region (id, post_id, region_id, created_at, modified_at)
VALUES (1, 1, 3, NOW(), NOW()), -- 자전거 - 강남구
       (2, 2, 3, NOW(), NOW()), -- 등산 장비 - 강남구
       (3, 3, 4, NOW(), NOW()), -- 골프채 - 서초구
       (4, 4, 5, NOW(), NOW()), -- 노트북 - 성남시
       (5, 5, 5, NOW(), NOW()), -- 카메라 - 성남시
       (6, 6, 6, NOW(), NOW()); -- 태블릿 - 수원시
