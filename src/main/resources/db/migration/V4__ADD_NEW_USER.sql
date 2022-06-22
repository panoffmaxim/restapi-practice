INSERT INTO `client` (`clientName`, `enabled`, `password`, `phone`)
VALUES ('test_role_user', true, '$2a$11$s3kqvCA3CTJ9e43Q5U/Fwen/bmpTtI16YMmnSdjCFCISlmHaWAW8.', '666');

INSERT INTO `client_roles` (`client_id`, `role_id`) SELECT r.id, c.id FROM `Role` AS r
CROSS JOIN `client` AS c WHERE r.`name`='ROLE_USER' AND c.`clientName` = 'test_role_user';
