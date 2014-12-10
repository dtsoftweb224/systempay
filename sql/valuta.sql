CREATE TABLE pay_system.valuta (
  data date DEFAULT NULL,
  numCode varchar(255) DEFAULT NULL,
  charCode varchar(255) DEFAULT NULL,
  nominal int(11) DEFAULT NULL,
  naz varchar(250) DEFAULT NULL,
  val double DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 131
CHARACTER SET utf8
COLLATE utf8_general_ci;