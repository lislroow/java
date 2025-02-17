/* sy_code_group */
INSERT INTO sy_code_group (cd_grp, cd_grp_nm) VALUES
('yn', '여부'),
('scientist:fos', 'scientist: field of study'),
('scientist:physics', 'scientist: physics'),
('search-mode:numeric', 'search: numeric search mode'),
('search-mode:char', 'search: character search mode')
;

/* sy_code */
INSERT INTO sy_code (cd_grp, seq, cd, cd_nm) VALUES
('yn', 1, 'Y', 'Y'),
('yn', 2, 'N', 'N'),
('search-mode:numeric', 1, 'eq', 'equal'),
('search-mode:numeric', 2, 'gt', 'greaterThan'),
('search-mode:numeric', 3, 'ge', 'greaterThanOrEqual'),
('search-mode:numeric', 4, 'lt', 'lessThan'),
('search-mode:numeric', 5, 'le', 'lessThanOrEqual'),
('search-mode:char', 1, 'eq', 'equal'),
('search-mode:char', 2, 'like', 'like'),
('search-mode:char', 3, 'sw', 'startsWith'),
('search-mode:char', 4, 'ew', 'endsWith'),
('scientist:fos', 1, 'P', 'physics'),
('scientist:fos', 2, 'C', 'chemistry'),
('scientist:fos', 3, 'M', 'mathematics'),
('scientist:fos', 4, 'B', 'biology'),
('scientist:fos', 5, 'A', 'astronomy'),
('scientist:physics', 1, 'C', 'Classical Mechanics'),
('scientist:physics', 2, 'E', 'Electromagnetic Mechanics'),
('scientist:physics', 3, 'Q', 'Quantum Mechanics'),
('scientist:physics', 4, 'F', 'Fluid Mechanics')
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

truncate table pt_scientist_image;
INSERT INTO pt_scientist_image (id, scientist_id, seq, image_desc, image_date, create_time, modify_time, create_id, modify_id) VALUES
('d8716b69-13aa-4b8f-ad69-5ce4a1a01305', 1, 1, '', '1637', now(), now(), '1', '1'),
('9f2177dc-c416-4550-92f2-ae43271c5a72', 1, 2, '', '', now(), now(), '1', '1'),
('e46c6df6-c139-4908-ba05-d33f430225c4', 2, 1, '', '1730', now(), now(), '1', '1'),
('0da9df68-73da-4187-802d-14a8873703ed', 3, 1, '', '1689', now(), now(), '1', '1'),
('e5643ad3-e8eb-48a5-9e0d-df354637fd38', 3, 2, '', '1702', now(), now(), '1', '1'),
('451ebdae-0874-4b6e-a286-1f385f2bad66', 4, 1, '', '1878', now(), now(), '1', '1'),
('4b4babda-28b2-43e5-9d46-682ab7e58957', 4, 2, '', '', now(), now(), '1', '1'),
('9bb1e3cf-959d-432e-863b-60af3d8efc1f', 4, 3, '', '', now(), now(), '1', '1'),
('ee0e7001-df39-4206-b12a-aa378446bc40', 5, 1, '', '1921', now(), now(), '1', '1'),
('bb55130d-b4f4-46e8-924b-5fceb5ea9714', 5, 2, '', '1940', now(), now(), '1', '1'),
('5c86b814-15aa-4867-acf0-45a69a34ac3a', 5, 3, '', '', now(), now(), '1', '1'),
('094b264a-94c5-4c63-9b0b-bc785ed79e8a', 6, 1, '', '2007', now(), now(), '1', '1'),
('c7f3a119-ad47-4441-af6c-f9c10f49cfd9', 7, 1, '', '', now(), now(), '1', '1'),
('f6e3ec36-b411-4c77-a939-0de6951b687a', 8, 1, '', '', now(), now(), '1', '1'),
('5ff86b75-4068-48d5-992a-581213d1dff8', 9, 1, '', '1860', now(), now(), '1', '1'),
('b25d7f4c-4857-45d5-80db-39d01f4887c8', 9, 1, '', '', now(), now(), '1', '1'),
('f7bafdb8-4d2b-4069-8d15-0bb0a8ad8373', 9, 1, '', '', now(), now(), '1', '1'),
('3e2590b7-86e3-4d61-9d32-74d1e24e13d8', 10, 1, '', '', now(), now(), '1', '1'),
('4b90c983-b1a1-45d8-8aa0-a50e2e9ae1d6', 11, 1, '', '', now(), now(), '1', '1'),
('d122e335-ffd5-4d0c-833b-18a7ea4cc854', 12, 1, '', '', now(), now(), '1', '1')
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

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Mercury', 2439.7, 3.3011e23, 5.791e7, 0.2056);

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Venus', 6051.8, 4.8675e24, 1.082e8, 0.0067);

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Earth', 6371.0, 5.97237e24, 1.496e8, 0.0167);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Moon', 1737.4, 7.35e22, 384400, 0.0549, 'Earth');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Mars', 3389.5, 6.4171e23, 2.279e8, 0.0934);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Phobos', 11.267, 1.08e16, 9377, 0.0151, 'Mars'),
(CURRVAL('pt_planet_id_seq'), 'Deimos', 6.2, 1.48e15, 23463, 0.0002, 'Mars');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Jupiter', 69911, 1.8982e27, 7.785e8, 0.0489);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Io', 1821.6, 8.93e22, 421700, 0.0041, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Europa', 1560.8, 4.8e22, 671034, 0.0094, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Ganymede', 2634.1, 1.48e23, 1070400, 0.0013, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Callisto', 2410.3, 1.08e23, 1882700, 0.0074, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Amalthea', 83.5, 2.08e18, 181000, 0.003, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Himalia', 85, 9.6e18, 11461000, 0.16, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Elara', 43, 8.7e17, 11741000, 0.22, 'Jupiter'),
(CURRVAL('pt_planet_id_seq'), 'Pasiphae', 30, 2.0e17, 23624000, 0.38, 'Jupiter');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Saturn', 58232, 5.6834e26, 1.433e9, 0.0565);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Titan', 2574.7, 1.35e23, 1221870, 0.0288, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Enceladus', 252.1, 1.08e20, 238042, 0.0047, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Rhea', 764.5, 2.31e21, 527108, 0.001, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Dione', 561.4, 1.1e21, 377396, 0.0022, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Tethys', 536.3, 6.17e20, 294619, 0.0001, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Mimas', 198.2, 3.75e19, 185539, 0.0196, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Hyperion', 135, 5.6e18, 1481010, 0.0232, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Phoebe', 106.5, 8.3e18, 12939000, 0.156, 'Saturn'),
(CURRVAL('pt_planet_id_seq'), 'Janus', 89.5, 1.9e18, 151472, 0.0068, 'Saturn');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Uranus', 25362, 8.6810e25, 2.877e9, 0.0463);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Titania', 788.4, 3.5e21, 436300, 0.0011, 'Uranus'),
(CURRVAL('pt_planet_id_seq'), 'Oberon', 761.4, 3.0e21, 583500, 0.0008, 'Uranus'),
(CURRVAL('pt_planet_id_seq'), 'Umbriel', 584.7, 1.27e21, 265970, 0.0039, 'Uranus'),
(CURRVAL('pt_planet_id_seq'), 'Ariel', 578.9, 1.29e21, 190930, 0.0012, 'Uranus'),
(CURRVAL('pt_planet_id_seq'), 'Miranda', 235.8, 6.6e19, 129900, 0.0013, 'Uranus');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Neptune', 24622, 1.02413e26, 4.503e9, 0.0097);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Triton', 1353.4, 2.14e22, 354800, 0.00002, 'Neptune'),
(CURRVAL('pt_planet_id_seq'), 'Nereid', 170, 3.1e19, 5513813, 0.7512, 'Neptune'),
(CURRVAL('pt_planet_id_seq'), 'Proteus', 210, 4.4e19, 117647, 0.00052, 'Neptune'),
(CURRVAL('pt_planet_id_seq'), 'Larissa', 96.5, 4.2e18, 73548, 0.0014, 'Neptune');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Pluto', 1188.3, 1.30900e22, 5.906e9, 0.2488);
INSERT INTO pt_satellite (planet_id, name, radius, mass, distance_from_planet, orbital_eccentricity, memo) VALUES
(CURRVAL('pt_planet_id_seq'), 'Charon', 606, 1.52e21, 19570, 0.0002, 'Pluto'),
(CURRVAL('pt_planet_id_seq'), 'Nix', 23, 4.5e16, 48694, 0.002, 'Pluto'),
(CURRVAL('pt_planet_id_seq'), 'Hydra', 30, 8.4e16, 64738, 0.0051, 'Pluto'),
(CURRVAL('pt_planet_id_seq'), 'Kerberos', 10, 1.6e16, 57783, 0.0032, 'Pluto'),
(CURRVAL('pt_planet_id_seq'), 'Styx', 8, 7.5e15, 42656, 0.0054, 'Pluto');

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Ceres', 473, 9.393e20, 4.14e8, 0.0758);

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Pallas', 272.5, 2.11e20, 4.14e8, 0.2312);

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Vesta', 262.7, 2.59e20, 3.53e8, 0.0890);

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Hygiea', 215, 8.32e19, 4.38e8, 0.1155);

INSERT INTO pt_planet (id, name, radius, mass, distance_from_sun, orbital_eccentricity) VALUES
(NEXTVAL('pt_planet_id_seq'), 'Eros', 8.42, 6.687e15, 2.18e8, 0.2229);




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
