DROP PROCEDURE IF EXISTS InsertRandomPosts;

DELIMITER //
CREATE PROCEDURE InsertRandomPosts(IN post_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE color CHAR(7);
    DECLARE member_id BINARY(16) DEFAULT UNHEX(REPLACE('4c29cb26-d2f3-38d8-9d02-a9da30c7ad99', '-', ''));
    DECLARE sql_text TEXT DEFAULT 'INSERT IGNORE INTO post (title, text, member_id, color) VALUES ';

    WHILE i < post_count
        DO
            SET color = CONCAT('#', LPAD(CONV(FLOOR(RAND(UNIX_TIMESTAMP() + i) * 16777215), 10, 16), 6, 0));
            SET sql_text =
                    CONCAT(sql_text, '(\'Post ', i + 1, '\', NULL, UNHEX(\'', HEX(member_id), '\'), \'', color, '\')');
            IF i < post_count - 1 THEN
                SET sql_text = CONCAT(sql_text, ', ');
            END IF;
            SET i = i + 1;
        END WHILE;

    SET @sql = sql_text;
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END//
DELIMITER ;

CALL InsertRandomPosts(80);