CREATE TABLE kommis (
  id int(11) NOT NULL,
  paySystem varchar(255) NOT NULL,
  komSystem double NOT NULL,
  kommis double NOT NULL,
  minKommis int(11) NOT NULL,
  maxKommis int(11) NOT NULL,  
  PRIMARY KEY (id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;