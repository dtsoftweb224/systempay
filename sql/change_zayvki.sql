CREATE TABLE IF NOT EXISTS change_zayvki(
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