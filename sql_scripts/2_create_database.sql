CREATE DATABASE IF NOT EXISTS my_spring_test DEFAULT CHARACTER SET utf8;

CREATE USER IF NOT EXISTS 'spring'@'localhost' IDENTIFIED BY '1234';

GRANT SELECT, INSERT, UPDATE, DELETE ON my_spring_test.* TO 'spring'@'localhost';

FLUSH PRIVILEGES;