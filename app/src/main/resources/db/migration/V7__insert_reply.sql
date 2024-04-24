DROP PROCEDURE IF EXISTS InsertRandomReplies;

DELIMITER //
CREATE PROCEDURE InsertRandomReplies(IN target_post_id INT, IN reply_count INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE member_id BINARY(16) DEFAULT UNHEX(REPLACE('4c29cb26-d2f3-38d8-9d02-a9da30c7ad99', '-', ''));
    DECLARE sql_text TEXT DEFAULT 'INSERT IGNORE INTO reply (post_id, content, member_id) VALUES ';

    WHILE i < reply_count
        DO
            SET sql_text =
                    CONCAT_WS('', sql_text, '(', target_post_id, ', \'comment ', i + 1, '.\', UNHEX(\'', HEX(member_id),
                              '\'))');
            IF i < reply_count - 1 THEN
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

CALL InsertRandomReplies(80, 40);