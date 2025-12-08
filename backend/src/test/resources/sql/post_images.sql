SET
FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE post_image;
SET
FOREIGN_KEY_CHECKS = 1;

INSERT INTO post_image (id, image_url, is_primary, post_id, created_at, modified_at)
VALUES (1, 'https://test.com/bike_1.jpg', TRUE, 1, NOW(), NOW()),
       (2, 'https://test.com/hiking_1.jpg', TRUE, 2, NOW(), NOW()),
       (3, 'https://test.com/golf_1.jpg', TRUE, 3, NOW(), NOW()),
       (4, 'https://test.com/laptop_1.jpg', TRUE, 4, NOW(), NOW()),
       (5, 'https://test.com/camera_1.jpg', TRUE, 5, NOW(), NOW()),
       (6, 'https://test.com/tablet_1.jpg', TRUE, 6, NOW(), NOW());
