insert into "VocabularySelection" (id, name, user_id) values (101, 'VS 100', 100);
insert into "VocabularySelection" (id, name, user_id) values (102, 'VS 100', 100);

insert into "VocabularyEntry" (id, meta, language, term, user_id) values (100, '{"translations": [{"translation": "how are you?"}]}', 'fr', 'ça va?', 100);
insert into "VocabularyEntry" (id, meta, language, term, user_id) values (101, '{"translations": [{"translation": "ça va?"}]}', 'en','how are you?', 100);
insert into "VocabularyEntry" (id, meta, language, term, user_id) values (102, '{"translations": [{"translation": "salut"}]}', 'en','hi', 100);

insert into "Repetition" (id, entry_id, selection_id, created) values (1, 100, 101, '2019-04-01 00:00:00');
insert into "Repetition" (id, entry_id, selection_id, created) values (2, 101, 102, '2019-04-02 00:00:00');
insert into "Repetition" (id, entry_id, selection_id, created) values (3, 102, 101, '2019-04-03 00:00:00');
