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
