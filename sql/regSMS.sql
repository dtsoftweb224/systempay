USE pay_system;
CREATE TABLE regSMS (
  id_sms int(11) NOT NULL,
  data date NOT NULL,
  telephone varchar(11) NOT NULL,
  status varchar(100) NOT NULL,  
  UNIQUE INDEX id_sms (id_sms)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;