USE pay_system;

CREATE TABLE bik (
  bik varchar(9) NOT NULL,
  ks varchar(20) NOT NULL,
  name varchar(255) NOT NULL,
  namemini varchar(100) NOT NULL,
  ind varchar(6),
  city varchar(40) NOT NULL,
  address varchar(150),
  phone varchar(50),
  okato varchar(2),
  okpo varchar(10),
  regnum varchar(10),
  dateadd DATE,
  tranzit varchar(100),
  UNIQUE INDEX bik (bik)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 131
CHARACTER SET utf8
COLLATE utf8_general_ci;