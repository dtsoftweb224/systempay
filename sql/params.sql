CREATE TABLE pay_system.params (
  id int(11) NOT NULL AUTO_INCREMENT,
  name_params varchar(50) NOT NULL,
  text_params varchar(255) DEFAULT NULL,
  value varchar(50) NOT NULL,
  comments varchar(255) DEFAULT NULL,  
  PRIMARY KEY (id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;