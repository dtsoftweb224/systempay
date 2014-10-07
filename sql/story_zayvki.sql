CREATE TABLE stroy_zayvki (
  id_story int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  id_zayvki varchar(50) NOT NULL,
  data datetime NOT NULL,
  type_zayvka varchar(100) NOT NULL,
  type_edit varchar(100) NOT NULL,
  status varchar(255) NOT NULL,
  user varchar(255),
  UNIQUE INDEX id (id_story),
  UNIQUE INDEX id_zayvki (id_zayvki)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;