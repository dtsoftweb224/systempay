CREATE TABLE zayvkicard (
  id int(11) NOT NULL AUTO_INCREMENT,
  wmid varchar(255) NOT NULL,
  date date NOT NULL,
  status varchar(100) NOT NULL,
  payOut varchar(255) NOT NULL,
  fioClient varchar(255) NOT NULL,
  summaPay double(10, 2) UNSIGNED NOT NULL,
  summaCard double(10, 2) UNSIGNED NOT NULL,
  payIn varchar(255) NOT NULL,
  kommis double(10, 2) NOT NULL,
  numberPay double(10, 2) UNSIGNED NOT NULL,
  valuta varchar(100) NOT NULL,
  mail varchar(255) DEFAULT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;