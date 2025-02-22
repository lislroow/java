DROP SEQUENCE IF EXISTS sq_user_id CASCADE ;
CREATE SEQUENCE IF NOT EXISTS sq_user_id
  INCREMENT BY 1
  START WITH 1
  MAXVALUE 99999
  CYCLE;

DROP TABLE IF EXISTS au_manager CASCADE ;
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

DROP TABLE IF EXISTS au_member CASCADE ;
CREATE TABLE IF NOT EXISTS au_member 
(
  id VARCHAR(16),
  login_id VARCHAR(255),
  login_pwd VARCHAR(60),
  roles VARCHAR(255),
  realname VARCHAR(255),
  nickname VARCHAR(255),
  enable_yn CHAR(1) DEFAULT 'Y',
  locked_yn CHAR(1) DEFAULT 'N',
  pwd_exp_date DATE DEFAULT CURRENT_DATE + INTERVAL '90 day',
  email_verify_yn CHAR(1) DEFAULT 'N',
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS au_member_oauth CASCADE ;
CREATE TABLE IF NOT EXISTS au_member_oauth 
(
  id VARCHAR(16),
  oauth2_id VARCHAR(50),
  registration_id VARCHAR(255),
  email VARCHAR(255),
  nickname VARCHAR(255),
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id, oauth2_id),
  CONSTRAINT fk_au_client_id FOREIGN KEY (id)
    REFERENCES au_member (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


DROP TABLE IF EXISTS au_client CASCADE ;
CREATE TABLE IF NOT EXISTS au_client
(
  id VARCHAR(16),
  contact_name VARCHAR(255),
  contact_email VARCHAR(255),
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS au_client_token CASCADE ;
CREATE TABLE IF NOT EXISTS au_client_token
(
  token_key VARCHAR(40),
  client_id VARCHAR(16),
  client_name VARCHAR(50),
  client_ip VARCHAR(255),
  roles VARCHAR(255) NOT NULL,
  enable_yn CHAR(1) DEFAULT 'Y',
  exp_date DATE DEFAULT CURRENT_DATE + INTERVAL '90 day',
  token_value TEXT NOT NULL,
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (token_key, client_id),
  CONSTRAINT fk_au_client_id FOREIGN KEY (client_id)
    REFERENCES au_client (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

