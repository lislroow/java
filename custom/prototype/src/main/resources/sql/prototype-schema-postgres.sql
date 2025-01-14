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
