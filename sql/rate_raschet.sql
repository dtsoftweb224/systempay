CREATE TABLE pay_system.rate_raschet (
  id int(11) NOT NULL AUTO_INCREMENT,
  valuta varchar(10) NOT NULL,
  operation varchar(10) NOT NULL,
  type_paschet varchar(20) NOT NULL,
  pay_system varchar(100) DEFAULT NULL,
  point varchar(100) NOT NULL,
  kommis double DEFAULT NULL,
  min_kom double DEFAULT NULL,
  max_kom double DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 65
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;