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
