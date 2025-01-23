DROP TABLE IF EXISTS sy_code_group CASCADE ;
CREATE TABLE IF NOT EXISTS sy_code_group
(
  cd_grp VARCHAR(20),
  cd_grp_nm VARCHAR(255),
  use_yn CHAR(1) NOT NULL CHECK (use_yn IN ('Y', 'N')) DEFAULT 'Y',
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (cd_grp)
);

DROP TABLE IF EXISTS sy_code CASCADE ;
CREATE TABLE IF NOT EXISTS sy_code
(
  cd_grp VARCHAR(20),
  cd VARCHAR(10),
  seq INT2 NULL,
  cd_nm VARCHAR(255),
  use_yn CHAR(1) NOT NULL CHECK (use_yn IN ('Y', 'N')) DEFAULT 'Y',
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (cd, cd_grp),
  CONSTRAINT fk_sy_code_cd_grp FOREIGN KEY (cd_grp)
    REFERENCES sy_code_group (cd_grp)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS pt_scientist CASCADE ;
CREATE TABLE IF NOT EXISTS pt_scientist 
(
  id SERIAL,
  name VARCHAR(255),
  birth_year INT4 NOT NULL,
  death_year INT4 NULL,
  fos_cd VARCHAR(255) NULL,
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pt_star CASCADE ;
CREATE TABLE IF NOT EXISTS pt_star (
  id SERIAL,
  name VARCHAR(255),
  distance DOUBLE PRECISION,
  brightness DOUBLE PRECISION,
  mass DOUBLE PRECISION,
  temperature INT,
  create_time timestamp DEFAULT now() NULL,
  modify_time timestamp DEFAULT now() NULL,
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pt_planet CASCADE ;
CREATE TABLE IF NOT EXISTS pt_planet (
  id SERIAL,
  name VARCHAR(50) NOT NULL,
  radius DOUBLE PRECISION NOT NULL,
  mass NUMERIC NOT NULL,
  distance_from_sun DOUBLE PRECISION NOT NULL,
  orbital_eccentricity FLOAT NOT NULL,
  deleted BOOLEAN DEFAULT false NOT NULL,
  create_time timestamp DEFAULT now() NULL,
  modify_time timestamp DEFAULT now() NULL,
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pt_satellite CASCADE ;
CREATE TABLE IF NOT EXISTS pt_satellite (
  id SERIAL,
  planet_id INT,
  name VARCHAR(255),
  radius DOUBLE PRECISION,
  mass NUMERIC,
  distance_from_planet INT,
  orbital_eccentricity DOUBLE PRECISION,
  memo VARCHAR(255),
  create_time timestamp DEFAULT now() NULL,
  modify_time timestamp DEFAULT now() NULL,
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id),
  CONSTRAINT fk_pt_planet_id FOREIGN KEY (planet_id)
    REFERENCES pt_planet (id)
    ON DELETE SET NULL
);


/*  pt_access_control  */
DROP TABLE IF EXISTS  pt_access_control CASCADE ;
CREATE TABLE IF NOT EXISTS  pt_access_control
(
  id SERIAL,
  ip_addr VARCHAR(15),
  delay_time TIMESTAMP,
  PRIMARY KEY (id)
);


/* pt_card */
DROP TABLE IF EXISTS pt_card CASCADE ;
CREATE TABLE IF NOT EXISTS pt_card 
(
  id SERIAL,
  card_no VARCHAR(16),
  issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  holder_name VARCHAR(100),
  PRIMARY KEY (id)
);


/* pt_card_payment */
DROP TABLE IF EXISTS pt_card_payment CASCADE ;
CREATE TABLE IF NOT EXISTS pt_card_payment 
(
  id SERIAL,
  card_no VARCHAR(16),
  paid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  paid_amount INT,
  store_name VARCHAR(100),
  PRIMARY KEY (id)
);
