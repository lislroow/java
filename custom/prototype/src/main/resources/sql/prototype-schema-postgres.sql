DROP TABLE IF EXISTS code_group CASCADE;
CREATE TABLE IF NOT EXISTS code_group
(
  cd_grp VARCHAR(10),
  cd_grp_nm VARCHAR(255),
  use_yn CHAR(1) NOT NULL CHECK (use_yn IN ('Y', 'N')) DEFAULT 'Y',
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (cd_grp)
);

DROP TABLE IF EXISTS code CASCADE;
CREATE TABLE IF NOT EXISTS code
(
  cd_grp VARCHAR(10),
  cd VARCHAR(10),
  seq INT2 NULL,
  cd_nm VARCHAR(255),
  use_yn CHAR(1) NOT NULL CHECK (use_yn IN ('Y', 'N')) DEFAULT 'Y',
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (cd, cd_grp),
  CONSTRAINT fk_code_cd_grp FOREIGN KEY (cd_grp)
    REFERENCES code_group (cd_grp)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS scientist;
CREATE TABLE IF NOT EXISTS scientist 
(
  id SERIAL,
  name VARCHAR(255),
  birth_year INT4 NOT NULL,
  death_year INT4 NULL,
  fos_cd VARCHAR(255) NULL,
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (id)
);


/* access_control */
DROP TABLE IF EXISTS access_control;
CREATE TABLE IF NOT EXISTS access_control 
(
  id SERIAL,
  ip_addr VARCHAR(15),
  delay_time TIMESTAMP,
  PRIMARY KEY (id)
);


/* card */
DROP TABLE IF EXISTS card;
CREATE TABLE IF NOT EXISTS card 
(
  id SERIAL,
  card_no VARCHAR(16),
  issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  holder_name VARCHAR(100),
  PRIMARY KEY (id)
);


/* card_payment */
DROP TABLE IF EXISTS card_payment;
CREATE TABLE IF NOT EXISTS card_payment 
(
  id SERIAL,
  card_no VARCHAR(16),
  paid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  paid_amount INT,
  store_name VARCHAR(100),
  PRIMARY KEY (id)
);
