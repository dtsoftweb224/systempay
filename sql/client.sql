CREATE TABLE pay_system.client (
  id int(11) NOT NULL AUTO_INCREMENT,
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  serial decimal(10, 0) DEFAULT NULL,
  number decimal(10, 0) DEFAULT NULL,
  fio varchar(255) NOT NULL,
  adress varchar(255) DEFAULT NULL,
  UNIQUE INDEX id (id),
  UNIQUE INDEX mail (mail)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 4096
CHARACTER SET utf8
COLLATE utf8_general_ci;