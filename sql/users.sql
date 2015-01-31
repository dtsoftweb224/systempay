CREATE TABLE pay_system.users (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  login varchar(255) NOT NULL,
  password varchar(255) DEFAULT NULL,
  type varchar(255) NOT NULL,
  fio varchar(255) NOT NULL,
  telephone varchar(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX login (login)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;