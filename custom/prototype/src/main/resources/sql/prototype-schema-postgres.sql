DROP TABLE IF EXISTS code_group CASCADE;
CREATE TABLE IF NOT EXISTS code_group
(
  cd_grp VARCHAR(10) PRIMARY KEY,
  cd_grp_nm VARCHAR(255),
  use_yn CHAR(1) NOT NULL CHECK (use_yn IN ('Y', 'N')) DEFAULT 'Y',
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
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
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1,
  PRIMARY KEY (cd, cd_grp),
  CONSTRAINT fk_code_cd_grp FOREIGN KEY (cd_grp)
    REFERENCES code_group (cd_grp)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS scientist;
CREATE TABLE IF NOT EXISTS scientist 
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  birth_year INT4 NOT NULL,
  death_year INT4 NULL,
  fos_cd VARCHAR(255) NULL,
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);


/* access_control */
DROP TABLE IF EXISTS access_control;
CREATE TABLE IF NOT EXISTS access_control 
(
  id SERIAL PRIMARY KEY,
  ip_addr VARCHAR(15),
  delay_time TIMESTAMP
);


/* card */
DROP TABLE IF EXISTS card;
CREATE TABLE IF NOT EXISTS card 
(
  id SERIAL PRIMARY KEY,
  card_no VARCHAR(16),
  issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  holder_name VARCHAR(100)
);


/* card_payment */
DROP TABLE IF EXISTS card_payment;
CREATE TABLE IF NOT EXISTS card_payment 
(
  id SERIAL PRIMARY KEY,
  card_no VARCHAR(16),
  paid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  paid_amount INT,
  store_name VARCHAR(100)
);
