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

insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S1hrggys4b3hh4', 'scott', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0qdyu0v35h5k6', 'tiger', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0s0xbssk4qvz9', 'ecycle', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0urv6uvvmd084', 'yslee', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0t8cf2ynjdw6q', 'sckim', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0rhofdnmqjs52', 'dhkim', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0qhkkc07160c2', 'jkpark', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0qeeyfij2z885', 'yhlee', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0qxyvj1lwgldj', 'jckim', now(), now(), 1, 1);
insert into employ (id, name, create_date, modify_date, create_id, modify_id) values('S0sl3e2kacak4i', 'mgkim', now(), now(), 1, 1);