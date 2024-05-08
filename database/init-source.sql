SET GLOBAL binlog_format = MIXED;
SET GLOBAL log_bin_trust_function_creators = 1;
SET GLOBAL server_id = 1;

CREATE USER 'replication'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
GRANT REPLICATION SLAVE ON *.* TO 'replication'@'%';
FLUSH PRIVILEGES;

FLUSH BINARY LOGS;

SHOW MASTER STATUS;