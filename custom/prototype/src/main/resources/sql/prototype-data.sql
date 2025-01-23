/* sy_code_group */
INSERT INTO sy_code_group (cd_grp, cd_grp_nm) VALUES
('USE_YN', '사용 여부'),
('LOCKED_YN', '잠김 여부'),
('ENABLE_YN', '활성화 여부'),
('DISABLED_YN', '비활성화 여부'),
('FOS', 'field of study'),
('FOSP', 'physics')
;

/* sy_code */
INSERT INTO sy_code (cd_grp, cd, seq, cd_nm) VALUES
('USE_YN', 'Y', 1, '사용'),
('USE_YN', 'N', 2, '미사용'),
('LOCKED_YN', 'Y', 1, '잠김'),
('LOCKED_YN', 'N', 2, '정상'),
('ENABLE_YN', 'Y', 1, '활성'),
('ENABLE_YN', 'N', 2, '비활성'),
('DISABLED_YN', 'Y', 1, '비활성'),
('DISABLED_YN', 'N', 2, '정상'),
('FOS', 'P', 1, 'physics'),
('FOS', 'C', 2, 'chemistry'),
('FOS', 'M', 3, 'mathematics'),
('FOS', 'B', 4, 'biology'),
('FOS', 'A', 5, 'astronomy'),
('FOSP', 'CM', 1, 'Classical Mechanics'),
('FOSP', 'EM', 2, 'Electromagnetic Mechanics'),
('FOSP', 'QM', 3, 'Quantum Mechanics'),
('FOSP', 'FM', 4, 'Fluid Mechanics')
;

INSERT INTO pt_scientist (name, birth_year, death_year, fos_cd) VALUES
('Galileo Galilei', 1564, 1642, 'P'),
('Johannes Kepler', 1571, 1630, 'A'),
('Isaac Newton', 1643, 1727, 'P'),
('Dmitri Mendeleev', 1834, 1907, 'C'),
('Albert Einstein', 1879, 1955, 'P'),
('Stephen Hawking', 1942, 2018, 'P'),
('Nikola Tesla', 1856, 1943, 'P'),
('Niels Bohr', 1885, 1962, 'P'),
('Michael Faraday', 1791, 1867, 'P'),
('James Clerk Maxwell', 1831, 1879, 'P'),
('Alan Turing', 1912, 1954, NULL),
('Richard Feynman', 1918, 1988, 'P')
;


INSERT INTO pt_star (name, distance, brightness, mass, temperature) VALUES
('Proxima Centauri', 4.24, 0.0017, 0.12, 3042),
('Sirius A', 8.6, 25.4, 2.06, 9940),
('Betelgeuse', 642.5, 126000, 20.0, 3500),
('Alpha Centauri A', 4.37, 1.519, 1.1, 5790),
('Alpha Centauri B', 4.37, 0.5, 0.907, 5260),
('Vega', 25.0, 40.12, 2.1, 9602),
('Rigel', 860.0, 120000, 21.0, 12100),
('Arcturus', 36.7, 170.0, 1.08, 4286),
('Polaris', 323.0, 2200, 5.4, 6015),
('Antares', 550.0, 57000, 12.4, 3200),
('Altair', 16.7, 10.6, 1.79, 7550),
('Deneb', 2615.0, 196000, 19.0, 8525)
;

INSERT INTO pt_satellites (name, radius, mass, planet_name, distance_from_planet, orbital_eccentricity) VALUES
('Moon', 1737.4, 7.35e22, 'Earth', 384400, 0.0549),
('Phobos', 11.267, 1.08e16, 'Mars', 9377, 0.0151),
('Deimos', 6.2, 1.48e15, 'Mars', 23463, 0.0002),
('Io', 1821.6, 8.93e22, 'Jupiter', 421700, 0.0041),
('Europa', 1560.8, 4.8e22, 'Jupiter', 671034, 0.0094),
('Ganymede', 2634.1, 1.48e23, 'Jupiter', 1070400, 0.0013),
('Callisto', 2410.3, 1.08e23, 'Jupiter', 1882700, 0.0074),
('Amalthea', 83.5, 2.08e18, 'Jupiter', 181000, 0.003),
('Himalia', 85, 9.6e18, 'Jupiter', 11461000, 0.16),
('Elara', 43, 8.7e17, 'Jupiter', 11741000, 0.22),
('Pasiphae', 30, 2.0e17, 'Jupiter', 23624000, 0.38),
('Titan', 2574.7, 1.35e23, 'Saturn', 1221870, 0.0288),
('Enceladus', 252.1, 1.08e20, 'Saturn', 238042, 0.0047),
('Rhea', 764.5, 2.31e21, 'Saturn', 527108, 0.001),
('Dione', 561.4, 1.1e21, 'Saturn', 377396, 0.0022),
('Tethys', 536.3, 6.17e20, 'Saturn', 294619, 0.0001),
('Mimas', 198.2, 3.75e19, 'Saturn', 185539, 0.0196),
('Hyperion', 135, 5.6e18, 'Saturn', 1481010, 0.0232),
('Phoebe', 106.5, 8.3e18, 'Saturn', 12939000, 0.156),
('Janus', 89.5, 1.9e18, 'Saturn', 151472, 0.0068),
('Titania', 788.4, 3.5e21, 'Uranus', 436300, 0.0011),
('Oberon', 761.4, 3.0e21, 'Uranus', 583500, 0.0008),
('Umbriel', 584.7, 1.27e21, 'Uranus', 265970, 0.0039),
('Ariel', 578.9, 1.29e21, 'Uranus', 190930, 0.0012),
('Miranda', 235.8, 6.6e19, 'Uranus', 129900, 0.0013),
('Triton', 1353.4, 2.14e22, 'Neptune', 354800, 0.00002),
('Nereid', 170, 3.1e19, 'Neptune', 5513813, 0.7512),
('Proteus', 210, 4.4e19, 'Neptune', 117647, 0.00052),
('Larissa', 96.5, 4.2e18, 'Neptune', 73548, 0.0014),
('Charon', 606, 1.52e21, 'Pluto', 19570, 0.0002),
('Nix', 23, 4.5e16, 'Pluto', 48694, 0.002),
('Hydra', 30, 8.4e16, 'Pluto', 64738, 0.0051),
('Kerberos', 10, 1.6e16, 'Pluto', 57783, 0.0032),
('Styx', 8, 7.5e15, 'Pluto', 42656, 0.0054);

/*  pt_access_control  */


/* pt_card */
-- [ H2 ]
--INSERT INTO pt_card (card_no, issue_date, holder_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-20 10:10:01', 'yyyy-MM-dd HH:mm:ss'), '홍길동');
-- [ postgres ]
INSERT INTO pt_card (card_no, issue_date, holder_name) VALUES
('1111222233334444', TO_TIMESTAMP('2024-12-20 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), '홍길동')
;


/* pt_card_payment */
-- [ H2 ]
--INSERT INTO pt_card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 13:37:01', 'yyyy-MM-dd HH:mm:ss'), 11600, '할리스커피당산역점');
--INSERT INTO pt_card_payment (card_no, paid_time, paid_amount, store_name) VALUES('1111222233334444', PARSEDATETIME('2024-12-21 19:34:45', 'yyyy-MM-dd HH:mm:ss'), 21250, '군산오징어당산역점');
-- [ postgres ]
INSERT INTO pt_card_payment (card_no, paid_time, paid_amount, store_name) VALUES
('1111222233334444', TO_TIMESTAMP('2024-12-21 13:37:01', 'YYYY-MM-DD HH24:MI:SS'), 11600, '할리스커피당산역점'),
('1111222233334444', TO_TIMESTAMP('2024-12-21 19:34:45', 'YYYY-MM-DD HH24:MI:SS'), 21250, '군산오징어당산역점')
;
