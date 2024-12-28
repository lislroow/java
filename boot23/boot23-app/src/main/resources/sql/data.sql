/* scientist */
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
INSERT INTO manager (email, passwd) VALUES('myeonggu.kim@kakao.com', '{bcrypt}$2a$10$vY/FCPvzZXkQ8ygnbU5DNuO0kS8FOG/RXw7ZTEtZA4DZSv9xTp2EC');


/* access_control */


/* card */
INSERT INTO card (card_no, issue_date, holder_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-20 10:10:01', 'yyyy-MM-dd HH:mm:ss'), '홍길동');


/* card_payment */
INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 13:37:01', 'yyyy-MM-dd HH:mm:ss'), 11600, '할리스커피당산역점');
INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 19:34:45', 'yyyy-MM-dd HH:mm:ss'), 21250, '군산오징어당산역점');
