/* Таблица хранения архивных заявок */
CREATE TABLE pay_system.arshive (
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

/* Таблица БИКов банков */
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

/* Таблица история изменений заявок */
CREATE TABLE pay_system.change_zayvki(
  id_change INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,  
  id_story INT(11) NOT NULL,
  param VARCHAR(100) NOT NULL,
  before_value VARCHAR(100) NOT NULL,
  after_value VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_change)
)
ENGINE = INNODB
AUTO_INCREMENT = 7
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица клиентов физ. лиц */
CREATE TABLE pay_system.client (
  id int(11) NOT NULL AUTO_INCREMENT,
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  serial decimal(10, 0) DEFAULT NULL,
  number decimal(10, 0) DEFAULT NULL,
  fio varchar(255) NOT NULL,
  adress varchar(255) DEFAULT NULL,
  UNIQUE INDEX id (id),
  UNIQUE INDEX mail (mail)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 4096
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица клиентов юр. лиц */
CREATE TABLE pay_system.client_ur (
  id int(11) NOT NULL AUTO_INCREMENT,
  ogrn varchar(20) NOT NULL,
  inn varchar(30) NOT NULL,
  kpp varchar(30) NOT NULL,
  fioDirector varchar(50),  
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица историй клиента */
CREATE TABLE pay_system.client_history (
  id int(11) NOT NULL AUTO_INCREMENT,
  mail varchar(255) NOT NULL,
  telephone varchar(12) DEFAULT NULL,
  id_platezha int(10) UNSIGNED NOT NULL,
  data date NOT NULL,
  summa double NOT NULL,
  payIn varchar(255) NOT NULL,
  payOut varchar(255) NOT NULL,
  fio varchar(255) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* История изменений заявки */
CREATE TABLE pay_system.handling_zayvki (
  id int(11) NOT NULL AUTO_INCREMENT,
  wmid varchar(255) NOT NULL,
  date date NOT NULL,
  status varchar(100) NOT NULL,
  payOut varchar(255) NOT NULL,
  summaPay double(10, 2) UNSIGNED NOT NULL,
  summaCard double(10, 2) UNSIGNED NOT NULL,
  payIn varchar(255) NOT NULL,
  kommis double(10, 2) NOT NULL,
  numberPay varchar(15) NOT NULL,
  valuta varchar(100) NOT NULL,
  mail varchar(255) NOT NULL,
  fio varchar(50) NOT NULL,
  date_born date DEFAULT NULL,
  pSerial decimal(10, 0) DEFAULT NULL,
  pNumber decimal(10, 0) DEFAULT NULL,
  pKod decimal(10, 0) DEFAULT NULL,
  pDate date DEFAULT NULL,
  numSchet varchar(20) DEFAULT NULL,
  telephone varchar(11) DEFAULT NULL,
  type varchar(100) NOT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

/* Учет обращений клиента */
CREATE TABLE pay_system.handlings (
  id int(11) NOT NULL AUTO_INCREMENT,
  idZayvki int(11) NOT NULL,
  idZayvki_rod int(11) NOT NULL,
  date date NOT NULL,
  status varchar(100) NOT NULL,
  komment varchar(255) NOT NULL,
  idClient int(11) DEFAULT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 11
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

/* Таблица логов - учет входа в систему */
CREATE TABLE pay_system.logs (
  id int(11) NOT NULL AUTO_INCREMENT,
  data DATETIME NOT NULL,
  login varchar(50) NOT NULL,
  program varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
AVG_ROW_LENGTH = 5461
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

/* Таблица для формирования номера заявки */
CREATE TABLE pay_system.numid (
  id int(11) NOT NULL AUTO_INCREMENT,
  numC int(11) NOT NULL,
  numB int(11) NOT NULL,
  UNIQUE INDEX id (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 2
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица дополнительных параметров */
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

/* Таблица платежных систем */
CREATE TABLE pay_system.pay_system (
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

/* Таблица тарифов */
CREATE TABLE pay_system.rate (
  id int(11) NOT NULL AUTO_INCREMENT,
  operation varchar(10) NOT NULL,
  type_raschet varchar(20) NOT NULL,
  pay_system varchar(100),
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

/* Таблица регистрации заявок */
CREATE TABLE pay_system.registry_zayvki (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  id_zayvki varchar(50) NOT NULL,
  data datetime NOT NULL,
  summa double NOT NULL,
  payIn varchar(255) NOT NULL,
  fioClient varchar(255) NOT NULL,
  mailClient varchar(255) NOT NULL,
  payOut varchar(255) NOT NULL,
  status varchar(255) DEFAULT NULL,
  ip_adress varchar(20) DEFAULT NULL,
  UNIQUE INDEX id (id),
  UNIQUE INDEX id_zayvki (id_zayvki)
)
ENGINE = INNODB
AUTO_INCREMENT = 9
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Регистрация отправленных СМС */
CREATE TABLE pay_system.regsms (
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

/* Внесенные изменения в заявку */
CREATE TABLE pay_system.stroy_zayvki (
  id_story int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  id_zayvki varchar(50) NOT NULL,
  data datetime NOT NULL,
  type_zayvka varchar(100) NOT NULL,
  type_edit varchar(100) NOT NULL,
  status varchar(255) NOT NULL,
  user varchar(255),
  UNIQUE INDEX id (id_story),
  UNIQUE INDEX id_zayvki (id_zayvki)
)
ENGINE = INNODB
AUTO_INCREMENT = 6
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица пользователей системы */
CREATE TABLE pay_system.users (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  login varchar(255) NOT NULL,
  password varchar(255) DEFAULT NULL,
  type varchar(255) NOT NULL,
  fio varchar(255) NOT NULL,
  telephone varchar(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX login (login)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица курсов валют */
CREATE TABLE pay_system.valuta (
  data date DEFAULT NULL,
  numCode varchar(255) DEFAULT NULL,
  charCode varchar(255) DEFAULT NULL,
  nominal int(11) DEFAULT NULL,
  naz varchar(250) DEFAULT NULL,
  val double DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 131
CHARACTER SET utf8
COLLATE utf8_general_ci;

/* Таблица заявок */
CREATE TABLE pay_system.zayvki (
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
  fio varchar(255) DEFAULT NULL,
  date_born date DEFAULT NULL,
  pSerial decimal(10, 0) DEFAULT NULL,
  pNumber decimal(10, 0) DEFAULT NULL,
  pKod decimal(10, 0) DEFAULT NULL,
  pDate date DEFAULT NULL,
  numSchet varchar(20) DEFAULT NULL,
  telephone varchar(11) DEFAULT NULL,
  typeOper varchar(100) NOT NULL,
  typeRaschet varchar(255) NOT NULL,
  UNIQUE INDEX id (id),
  INDEX mail (mail),
  UNIQUE INDEX numPay (numberPay)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;