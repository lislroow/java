/* code_group */
INSERT INTO code_group (cd_grp, cd_grp_nm) VALUES('USE_YN', '사용 여부');
INSERT INTO code_group (cd_grp, cd_grp_nm) VALUES('LOCKED_YN', '잠김 여부');
INSERT INTO code_group (cd_grp, cd_grp_nm) VALUES('DISABLED_YN', '비활성화 여부');
INSERT INTO code_group (cd_grp, cd_grp_nm) VALUES('FOS', 'field of study');
INSERT INTO code_group (cd_grp, cd_grp_nm) VALUES('FOSP', 'physics');

/* code */
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('USE_YN', 'Y', 1, '사용');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('USE_YN', 'N', 2, '미사용');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('LOCKED_YN', 'Y', 1, '잠김');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('LOCKED_YN', 'N', 2, '정상');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('DISABLED_YN', 'Y', 1, '비활성');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('DISABLED_YN', 'N', 2, '정상');

INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOS', 'P', 1, 'physics');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOS', 'C', 2, 'chemistry');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOS', 'M', 3, 'mathematics');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOS', 'B', 4, 'biology');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOS', 'A', 5, 'astronomy');

INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOSP', 'CM', 1, 'Classical Mechanics');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOSP', 'EM', 2, 'Electromagnetic Mechanics');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOSP', 'QM', 3, 'Quantum Mechanics');
INSERT INTO code (cd_grp, cd, seq, cd_nm) VALUES('FOSP', 'FM', 4, 'Fluid Mechanics');


INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Galileo Galilei', 1564, 1642, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Johannes Kepler', 1571, 1630, 'A');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Isaac Newton', 1643, 1727, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Dmitri Mendeleev', 1834, 1907, 'C');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Albert Einstein', 1879, 1955, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Stephen Hawking', 1942, 2018, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Nikola Tesla', 1856, 1943, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Niels Bohr', 1885, 1962, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Michael Faraday', 1791, 1867, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('James Clerk Maxwell', 1831, 1879, 'P');
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Alan Turing', 1912, 1954, NULL);
INSERT INTO scientist (name, birth_year, death_year, fos_cd) VALUES('Richard Feynman', 1918, 1988, 'P');


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
