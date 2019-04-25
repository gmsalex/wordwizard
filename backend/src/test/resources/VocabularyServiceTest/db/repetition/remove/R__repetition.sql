insert into "VocabularySelection" (id, name, user_id) values (101, 'VS 100', 100);
insert into "VocabularySelection" (id, name, user_id) values (102, 'VS 100', 100);

insert into "VocabularyEntry" (meta, created, language, term, user_id, id) values ('{"translations": ["how are you?"]}', '2019-04-01 00:00:00', 'fr', 'ça va?', 100, 100);
insert into "VocabularyEntry" (meta, created, language, term, user_id, id) values ('{"translations": ["ça va?"]}', '2019-04-01 00:00:00', 'en','how are you?', 100, 101);

insert into "Repetition" (entry_id, selection_id, id) values (100, 101, 1);
insert into "Repetition" (entry_id, selection_id, id) values (101, 102, 2);
insert into "Repetition" (entry_id, selection_id, id) values (100, 101, 3);
