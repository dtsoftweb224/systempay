USE pay_system;

CREATE TABLE pay_system.handlings (
  id int(11) NOT NULL AUTO_INCREMENT,
  idZayvki int(11) NOT NULL,
  idZayvki_rod int(11) NOT NULL,
  date date NOT NULL,
  status varchar(100) NOT NULL,
  komment varchar(255) NOT NULL,
  idClient int(11) DEFAULT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 11
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;