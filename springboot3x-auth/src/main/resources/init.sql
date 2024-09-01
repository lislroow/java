drop table if exists tb_user;

create table if not exists tb_user
(
 id varchar(14),
 password varchar(255),
 email varchar(255),
 primary key (id)
);

insert into tb_user (id, password, email) values('01hrggys4b3hh4', '$2a$10$ORbBqVbeTlyirL8AhbXDKO5vWRtvaoo7hDrV0JdFdHD2GTtpm4Koi', 'hi@mgkim.net');
insert into tb_user (id, password, email) values('00s0xbssk4qvz9', '$2a$10$ORbBqVbeTlyirL8AhbXDKO5vWRtvaoo7hDrV0JdFdHD2GTtpm4Koi', 'mgkim.net@gmail.com');
