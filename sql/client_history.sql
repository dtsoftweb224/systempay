CREATE TABLE client_history (
  id int(11) NOT NULL AUTO_INCREMENT,
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  id_platezha int(10) UNSIGNED NOT NULL,
  data date NOT NULL,
  summa double NOT NULL,
  payIn varchar(255) NOT NULL,
  payOut varchar(255) NOT NULL,
  fio varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;