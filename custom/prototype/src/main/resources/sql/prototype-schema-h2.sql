DROP TABLE IF EXISTS scientist;
CREATE TABLE IF NOT EXISTS pt_scientist 
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  birth_year INT NOT NULL,
  death_year INT NULL,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modify_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);

/*  pt_access_control  */
DROP TABLE IF EXISTS  pt_access_control ;
CREATE TABLE IF NOT EXISTS  pt_access_control  
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  ip_addr VARCHAR(15),
  delay_time TIMESTAMP
);


/* pt_card */
DROP TABLE IF EXISTS card;
CREATE TABLE IF NOT EXISTS pt_card 
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  card_no VARCHAR(16),
  issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  holder_name VARCHAR(100)
);


/* pt_card_payment */
DROP TABLE IF EXISTS card_payment;
CREATE TABLE IF NOT EXISTS pt_card_payment 
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  card_no VARCHAR(16),
  paid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  paid_amount INT,
  store_name VARCHAR(100)
);
