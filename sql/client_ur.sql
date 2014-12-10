CREATE TABLE client_ur (
  id int(11) NOT NULL,
  ogrn varchar(20) NOT NULL,
  inn varchar(30) NOT NULL,
  kpp varchar(30) NOT NULL,
  fioDirector varchar(50),  
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  PRIMARY KEY (ogrn)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;