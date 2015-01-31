CREATE TABLE pay_system.bik (
  id int(11) NOT NULL AUTO_INCREMENT,
  bik varchar(9) NOT NULL,
  ks varchar(20) NOT NULL,
  name varchar(255) NOT NULL,
  namemini varchar(100) NOT NULL,
  ind varchar(6) DEFAULT NULL,
  city varchar(40) NOT NULL,
  address varchar(150) DEFAULT NULL,
  phone varchar(50) DEFAULT NULL,
  okato varchar(2) DEFAULT NULL,
  okpo varchar(10) DEFAULT NULL,
  regnum varchar(10) DEFAULT NULL,
  dateadd date DEFAULT NULL,
  tranzit varchar(100) DEFAULT NULL,
  UNIQUE INDEX bik (bik),
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 6631
AVG_ROW_LENGTH = 131
CHARACTER SET utf8
COLLATE utf8_general_ci;