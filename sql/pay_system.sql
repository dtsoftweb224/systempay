CREATE TABLE pay_system (
  id int(11) NOT NULL AUTO_INCREMENT,  
  pay varchar(50) NOT NULL,
  list_val varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;