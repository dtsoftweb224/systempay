CREATE TABLE rate (
  id int(11) NOT NULL AUTO_INCREMENT,
  operation varchar(10) NOT NULL,
  type_paschet varchar(20) NOT NULL,
  pay_system varchar(100) DEFAULT NULL,
  point varchar(100) NOT NULL,
  val_point varchar(100) DEFAULT NULL,
  min_kom varchar(100) DEFAULT NULL,
  max_kom varchar(100) DEFAULT NULL,
  state tinyint(1) NOT NULL,
  name_valut varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;
