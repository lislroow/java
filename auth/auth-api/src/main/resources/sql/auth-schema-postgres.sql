DROP SEQUENCE IF EXISTS sq_user_id;
CREATE SEQUENCE IF NOT EXISTS sq_user_id
  INCREMENT BY 1
  START WITH 1
  MAXVALUE 99999
  CYCLE;

DROP TABLE IF EXISTS au_manager;
CREATE TABLE IF NOT EXISTS au_manager
(
  id VARCHAR(16),
  login_id VARCHAR(255),
  login_pwd VARCHAR(60),
  roles VARCHAR(255),
  mgr_name VARCHAR(255),
  enable_yn CHAR(1) DEFAULT 'Y',
  locked_yn CHAR(1) DEFAULT 'N',
  pwd_exp_date DATE DEFAULT CURRENT_DATE + INTERVAL '90 day',
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS au_member;
CREATE TABLE IF NOT EXISTS au_member 
(
  id VARCHAR(16),
  login_id VARCHAR(255),
  login_pwd VARCHAR(60),
  roles VARCHAR(255),
  realname VARCHAR(255),
  registration_id VARCHAR(255),
  oauth2_id VARCHAR(255),
  nickname VARCHAR(255),
  enable_yn CHAR(1) DEFAULT 'Y',
  locked_yn CHAR(1) DEFAULT 'N',
  pwd_exp_date DATE DEFAULT CURRENT_DATE + INTERVAL '90 day',
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS au_client CASCADE;
CREATE TABLE IF NOT EXISTS au_client
(
  id VARCHAR(16),
  client_name VARCHAR(50),
  client_ip VARCHAR(255),
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS au_client_token;
CREATE TABLE IF NOT EXISTS au_client_token
(
  token_id VARCHAR(40),
  client_id VARCHAR(16),
  enable_yn CHAR(1) DEFAULT 'Y',
  locked_yn CHAR(1) DEFAULT 'N',
  token TEXT,
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (token_id, client_id),
  CONSTRAINT fk_au_client_id FOREIGN KEY (client_id)
    REFERENCES au_client (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
