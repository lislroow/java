/*
DROP TABLE scientist;
DROP SEQUENCE scientist_seq;
*/

CREATE SEQUENCE scientist_seq START 1 INCREMENT 1;

CREATE TABLE scientist 
(
  id INT DEFAULT NEXTVAL('scientist_seq') PRIMARY KEY,
  name VARCHAR(255),
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modify_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
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
