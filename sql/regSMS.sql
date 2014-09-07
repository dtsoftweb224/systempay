CREATE TABLE regsms (
  id_sms int(11) NOT NULL,
  data date NOT NULL,
  telephone varchar(11) NOT NULL,
  status varchar(100) NOT NULL,
  numPay varchar(50) NOT NULL,
  PRIMARY KEY (id_sms),
  UNIQUE INDEX id_sms (id_sms)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;