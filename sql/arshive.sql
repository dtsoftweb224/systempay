﻿CREATE TABLE pay_system.arshive (
  id int(11) NOT NULL AUTO_INCREMENT,
  wmid varchar(255) NOT NULL,
  date datetime NOT NULL,
  status varchar(100) NOT NULL,
  payOut varchar(255) NOT NULL,
  summaPay double(10, 2) UNSIGNED NOT NULL,
  summaCard double(10, 2) UNSIGNED NOT NULL,
  payIn varchar(255) NOT NULL,
  kommis double(10, 2) NOT NULL,
  numberPay varchar(15) NOT NULL,
  valuta varchar(100) NOT NULL,
  mail varchar(255) NOT NULL,
  fio varchar(255) NOT NULL,
  date_born date DEFAULT NULL,
  pSerial decimal(10, 0) DEFAULT NULL,
  pNumber decimal(10, 0) DEFAULT NULL,
  pKod decimal(10, 0) DEFAULT NULL,
  pDate date DEFAULT NULL,
  numSchet varchar(20) DEFAULT NULL,
  telephone varchar(11) DEFAULT NULL,
  typeOper varchar(100) NOT NULL,
  typeRaschet varchar(255) NOT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;