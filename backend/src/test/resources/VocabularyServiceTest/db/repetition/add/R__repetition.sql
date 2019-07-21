insert into "VocabularySelection" (id, name, user_id) values (101, 'VS 100', 100);
insert into "VocabularySelection" (id, name, user_id) values (200, 'VS 200', 200);

insert into "VocabularyEntry" (id, meta, language, term, user_id) values (100, '{"translations": [{"translation": "how are you?"}]}', 'fr', 'ça va?', 100);
insert into "VocabularyEntry" (id, meta, language, term, user_id) values (101, '{"translations": [{"translation": "how are you?"}]}', 'en','how are you?', 100);
