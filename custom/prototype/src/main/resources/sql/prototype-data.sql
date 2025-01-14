INSERT INTO scientist (name, birth_year, death_year) VALUES('Galileo Galilei', 1564, 1642);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Johannes Kepler', 1571, 1630);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Isaac Newton', 1643, 1727);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Dmitri Mendeleev', 1834, 1907);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Albert Einstein', 1879, 1955);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Stephen Hawking', 1942, 2018);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Nikola Tesla', 1856, 1943);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Niels Bohr', 1885, 1962);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Michael Faraday', 1791, 1867);
INSERT INTO scientist (name, birth_year, death_year) VALUES('James Clerk Maxwell', 1831, 1879);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Alan Turing', 1912, 1954);
INSERT INTO scientist (name, birth_year, death_year) VALUES('Richard Feynman', 1918, 1988);

/* access_control */


/* card */
-- [ H2 ]
--INSERT INTO card (card_no, issue_date, holder_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-20 10:10:01', 'yyyy-MM-dd HH:mm:ss'), '홍길동');
-- [ postgres ]
INSERT INTO card (card_no, issue_date, holder_name) VALUES('1111222233334444', TO_TIMESTAMP('2024-12-20 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), '홍길동');


/* card_payment */
-- [ H2 ]
--INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 13:37:01', 'yyyy-MM-dd HH:mm:ss'), 11600, '할리스커피당산역점');
--INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 19:34:45', 'yyyy-MM-dd HH:mm:ss'), 21250, '군산오징어당산역점');
-- [ postgres ]
INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', TO_TIMESTAMP('2024-12-21 13:37:01', 'YYYY-MM-DD HH24:MI:SS'), 11600, '할리스커피당산역점');
INSERT INTO card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', TO_TIMESTAMP('2024-12-21 19:34:45', 'YYYY-MM-DD HH24:MI:SS'), 21250, '군산오징어당산역점');
