create sequence hibernate_sequence start 1 increment 1;
create table "User"
(
  id     int8         not null,
  active boolean      not null,
  name   varchar(255) not null,
  primary key (id)
);
create table "UserAuthEmail"
(
  email    varchar(255),
  password varchar(255) not null,
  user_id  int8         not null,
  primary key (user_id)
);
alter table "UserAuthEmail"
  add constraint FKhg2mdbbiunb63wjjhou4r6j43 foreign key (user_id) references "User";
