/* scientist */
DROP TABLE IF EXISTS scientist;
CREATE TABLE IF NOT EXISTS scientist 
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modify_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);
INSERT INTO scientist (name) VALUES('Galileo Galilei');
INSERT INTO scientist (name) VALUES('Johannes Kepler');
INSERT INTO scientist (name) VALUES('Isaac Newton');
INSERT INTO scientist (name) VALUES('Dmitri Mendeleev');
INSERT INTO scientist (name) VALUES('Albert Einstein');
INSERT INTO scientist (name) VALUES('Stephen Hawking');
INSERT INTO scientist (name) VALUES('Nikola Tesla');
INSERT INTO scientist (name) VALUES('Niels Bohr');
INSERT INTO scientist (name) VALUES('Michael Faraday');
INSERT INTO scientist (name) VALUES('James Clerk Maxwell');
INSERT INTO scientist (name) VALUES('Alan Turing');
INSERT INTO scientist (name) VALUES('Richard Feynman');

/* manager */
DROP TABLE IF EXISTS manager;
CREATE TABLE IF NOT EXISTS manager
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  passwd VARCHAR(1024),
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modify_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  create_id INT DEFAULT 1,
  modify_id INT DEFAULT 1
);
INSERT INTO manager (email, passwd) VALUES('myeonggu.kim@kakao.com', '{bcrypt}$2a$10$vY/FCPvzZXkQ8ygnbU5DNuO0kS8FOG/RXw7ZTEtZA4DZSv9xTp2EC');


/* access_control */
DROP TABLE IF EXISTS access_control;
CREATE TABLE IF NOT EXISTS access_control 
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  ip_addr VARCHAR(15),
  delay_time TIMESTAMP
);


/* card */
DROP TABLE IF EXISTS card;
CREATE TABLE IF NOT EXISTS card 
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  card_no VARCHAR(16),
  issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  holder_name VARCHAR(100)
);
INSERT INTO card (card_no, issue_date, holder_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-20 10:10:01', 'yyyy-MM-dd HH:mm:ss'), '홍길동');


/* card_payment */
DROP TABLE IF EXISTS card_payment;
CREATE TABLE IF NOT EXISTS card_payment 
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  card_no VARCHAR(16),
  paid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  paid_amount INT,
  store_name VARCHAR(100)
);
INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 13:37:01', 'yyyy-MM-dd HH:mm:ss'), 11600, '할리스커피당산역점');
INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 19:34:45', 'yyyy-MM-dd HH:mm:ss'), 21250, '군산오징어당산역점');
