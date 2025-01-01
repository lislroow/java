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
  email VARCHAR(255),
  passwd VARCHAR(1024),
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);
