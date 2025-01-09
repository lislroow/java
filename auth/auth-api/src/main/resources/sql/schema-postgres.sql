DROP TABLE IF EXISTS member;
CREATE TABLE IF NOT EXISTS member 
(
  id SERIAL PRIMARY KEY,
  registration_id VARCHAR(255),
  oauth2_id VARCHAR(255),
  email VARCHAR(255),
  nickname VARCHAR(255),
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id SMALLINT DEFAULT 1,
  modify_id SMALLINT DEFAULT 1
);

DROP TABLE IF EXISTS scientist;
CREATE TABLE IF NOT EXISTS scientist 
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id SMALLINT DEFAULT 1,
  modify_id SMALLINT DEFAULT 1
);

DROP TABLE IF EXISTS manager;
CREATE TABLE IF NOT EXISTS manager
(
  id SERIAL PRIMARY KEY,
  mgr_id VARCHAR(255),
  mgr_name VARCHAR(255),
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);

DROP TABLE IF EXISTS token;
CREATE TABLE IF NOT EXISTS token
(
  id VARCHAR(42) PRIMARY KEY,
  token TEXT,
  client_ip VARCHAR(255),
  use_yn CHAR(1) DEFAULT 'Y',
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);
