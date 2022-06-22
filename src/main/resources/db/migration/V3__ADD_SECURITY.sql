CREATE TABLE `Privilege` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `roles_privileges` (
  `role_id` bigint NOT NULL,
  `privilege_id` bigint NOT NULL,
  FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`),
  FOREIGN KEY (`privilege_id`) REFERENCES `Privilege` (`id`)
);

CREATE TABLE `client_roles` (
  `client_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)
);

INSERT INTO `Privilege` (`name`) VALUES ('READ_PRIVILEGE');
INSERT INTO `Privilege` (`name`) VALUES ('WRITE_PRIVILEGE');

INSERT INTO `Role` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `Role` (`name`) VALUES ('ROLE_USER');

INSERT INTO `roles_privileges` (`role_id`, `privilege_id`) SELECT r.id, p.id FROM `Role` AS r
CROSS JOIN `Privilege` AS p WHERE r.`name`='ROLE_ADMIN' AND p.`name` = 'READ_PRIVILEGE';

INSERT INTO `roles_privileges` (`role_id`, `privilege_id`) SELECT r.id, p.id FROM `Role` AS r
CROSS JOIN `Privilege` AS p WHERE r.`name`='ROLE_ADMIN' AND p.`name` = 'WRITE_PRIVILEGE';

INSERT INTO `roles_privileges` (`role_id`, `privilege_id`) SELECT r.id, p.id FROM `Role` AS r
CROSS JOIN `Privilege` AS p WHERE r.`name`='ROLE_USER' AND p.`name` = 'READ_PRIVILEGE';

ALTER TABLE `client` ADD `enabled` BIT;
ALTER TABLE `client` ADD `password` varchar(255);

INSERT INTO `client` (`clientName`, `enabled`, `password`, `phone`)
VALUES ('Test', true, '$2a$11$s3kqvCA3CTJ9e43Q5U/Fwen/bmpTtI16YMmnSdjCFCISlmHaWAW8.', '777');

INSERT INTO `client_roles` (`client_id`, `role_id`) SELECT r.id, c.id FROM `Role` AS r
CROSS JOIN `client` AS c WHERE r.`name`='ROLE_ADMIN' AND c.`clientName` = 'Test';
