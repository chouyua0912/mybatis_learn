create sequence blog_seq;

create table blog (
  id integer primary key,
  name varchar(128)
);

create sequence user_info_seq;

create table user_info (
  id integer primary key,
  name varchar(128),
  address varchar(256),
  phone varchar(128)
)