insert into "VocabularySelection" (id, name, user_id) values (101, 'VS 100', 100);
insert into "VocabularySelection" (id, name, user_id) values (200, 'VS 200', 200);

insert into "VocabularyEntry" (meta, created, language, term, user_id, id) values ('{"translations": ["how are you?"]}', '2019-04-01 00:00:00', 'fr', 'ça va?', 100, 100);
insert into "VocabularyEntry" (meta, created, language, term, user_id, id) values ('{"translations": ["ça va?"]}', '2019-04-01 00:00:00', 'en','how are you?', 100, 101);
