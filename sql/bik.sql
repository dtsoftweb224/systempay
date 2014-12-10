USE pay_system;

CREATE TABLE pay_system.bik (
  bik decimal(10, 0) NOT NULL,
  swift varchar(255) NOT NULL,
  bank varchar(255) NOT NULL,
  nal varchar(20),
  beznal varchar(20),
  UNIQUE INDEX bik (bik)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 131
CHARACTER SET utf8
COLLATE utf8_general_ci;