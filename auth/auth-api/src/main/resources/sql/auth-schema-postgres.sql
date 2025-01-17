DROP SEQUENCE IF EXISTS sq_user_id;
CREATE SEQUENCE IF NOT EXISTS sq_user_id
  INCREMENT BY 1
  START WITH 1
  MAXVALUE 99999
  CYCLE;


DROP TABLE IF EXISTS member;
CREATE TABLE IF NOT EXISTS member 
(
  id VARCHAR(15),
  registration_id VARCHAR(255),
  oauth2_id VARCHAR(255),
  login_id VARCHAR(255),
  nickname VARCHAR(255),
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS manager;
CREATE TABLE IF NOT EXISTS manager
(
  id VARCHAR(15),
  login_id VARCHAR(255),
  login_pwd VARCHAR(70),
  mgr_name VARCHAR(255),
  disabled_yn CHAR(1) DEFAULT 'N',
  locked_yn CHAR(1) DEFAULT 'N',
  pwd_exp_date DATE DEFAULT CURRENT_DATE + INTERVAL '90 day',
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS openapi_user;
CREATE TABLE IF NOT EXISTS openapi_user
(
  id VARCHAR(15),
  login_id VARCHAR(255),
  user_name VARCHAR(255),
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS token;
CREATE TABLE IF NOT EXISTS token
(
  token_id VARCHAR(42),
  id VARCHAR(15),
  client_ip VARCHAR(255),
  use_yn CHAR(1) DEFAULT 'N',
  token TEXT,
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(15) DEFAULT '1',
  modify_id VARCHAR(15) DEFAULT '1',
  PRIMARY KEY (token_id)
);
