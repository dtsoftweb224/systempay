CREATE TABLE client (
  id int(11) NOT NULL,
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  serial decimal(10, 0) NOT NULL,
  number decimal(10, 0) NOT NULL,
  fio varchar(255) NOT NULL,
  address varchar(255) DEFAULT NULL,
  PRIMARY KEY (mail)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;