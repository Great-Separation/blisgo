SET @member_id = UNHEX(REPLACE('4c29cb26-d2f3-38d8-9d02-a9da30c7ad99', '-', ''));

INSERT IGNORE INTO dogam (member_id, waste_id)
VALUES (@member_id, 1019);