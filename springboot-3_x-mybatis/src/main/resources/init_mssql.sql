drop table if exists employ;

create table if not exists employ 
(
 id varchar(14),
 name varchar(255),
 create_date timestamp,
 modify_date timestamp,
 create_id varchar(14),
 modify_id varchar(14),
 primary key (id)
);

insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('01hrggys4b3hh4', 'scott', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00qdyu0v35h5k6', 'tiger', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00s0xbssk4qvz9', 'ecycle', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00urv6uvvmd084', 'yslee', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00t8cf2ynjdw6q', 'sckim', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00rhofdnmqjs52', 'dhkim', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00qhkkc07160c2', 'jkpark', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00qeeyfij2z885', 'yhlee', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00qxyvj1lwgldj', 'jckim', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('00sl3e2kacak4i', 'mgkim', now(), now(), 1, 1);

create table if not exists employ_mssql
(
 id varchar(14),
 name varchar(255),
 create_date timestamp,
 modify_date timestamp,
 create_id varchar(14),
 modify_id varchar(14),
 primary key (id)
);

insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_1', 'scott', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_2', 'tiger', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_3', 'ecycle', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_4', 'yslee', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_5', 'sckim', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_6', 'dhkim', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_7', 'jkpark', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_8', 'yhlee', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_9', 'jckim', now(), now(), 1, 1);
insert into employ_mssql (id, name, create_date, modify_date, create_id, modify_id) values('mssql_0', 'mgkim', now(), now(), 1, 1);
