create sequence hibernate_sequence start 1 increment 1;

create table "Repetition"
(
  id           int8 not null,
  entry_id     int8 not null,
  selection_id int8 not null,
  primary key (id)
);

create table "User"
(
  id     int8         not null,
  active boolean      not null,
  name   varchar(255) not null,
  primary key (id)
);

create table "UserAuthEmail"
(
  email    varchar(255) not null,
  password varchar(255) not null,
  user_id  int8         not null,
  primary key (user_id)
);

create table "VocabularyEntry"
(
  id       int8       not null,
  meta     jsonb      not null,
  created  timestamp  not null,
  language varchar(3) not null,
  term     text       not null,
  user_id  int8       not null,
  primary key (id)
);
create table "VocabularySelection"
(
  id      int8        not null,
  name    varchar(64) not null,
  user_id int8        not null,
  primary key (id)
);

alter table "Repetition"
  add constraint FKoo3rvmhaq3yq4hstmfyl3m4rr foreign key (entry_id) references "VocabularyEntry";

alter table "Repetition"
  add constraint FK8nt6c1drj8k18wks9qbelv20a foreign key (selection_id) references "VocabularySelection";

alter table "UserAuthEmail"
  add constraint FKhg2mdbbiunb63wjjhou4r6j43 foreign key (user_id) references "User";

alter table "VocabularyEntry"
  add constraint FKe037xqo54b0989d6msbn4pcvr foreign key (user_id) references "User";

alter table "VocabularySelection"
  add constraint FKe2c0nte8fi9x67nhwoe8aci7h foreign key (user_id) references "User";

