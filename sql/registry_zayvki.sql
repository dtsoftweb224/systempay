CREATE TABLE registry_zayvki (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  id_zayvki varchar(50) NOT NULL,
  data datetime NOT NULL,
  summa double NOT NULL,
  payIn varchar(255) NOT NULL,
  fioClient varchar(255) NOT NULL,
  mailClient varchar(255) NOT NULL,
  payOut varchar(255) NOT NULL,
  UNIQUE INDEX id (id),
  UNIQUE INDEX id_zayvki (id_zayvki)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;