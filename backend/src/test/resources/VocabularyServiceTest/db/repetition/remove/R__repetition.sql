insert into "VocabularyEntry" (id, meta, language, term, user_id, created) values (100, '{"usages": [{"value": "how are you?"}]}', 'fr', 'ça va?', 100, '2019-04-01 00:00:00');
insert into "VocabularyEntry" (id, meta, language, term, user_id, created) values (101, '{"usages": [{"value": "ça va?"}]}', 'en','how are you?', 100, '2019-04-01 00:00:01');
insert into "VocabularyEntry" (id, meta, language, term, user_id, created) values (102, '{"usages": [{"value": "salut"}]}', 'en','hi', 100, '2019-04-01 00:00:02');


insert into "VocabularyTag" (id, name, user_id) values (101, 'Tag 101', 100);
insert into "VocabularyTag" (id, name, user_id) values (102, 'Tag 102', 100);

insert into "Repetition" (id, entry_id, tag_id) values (1, 100, 101);
insert into "Repetition" (id, entry_id, tag_id) values (2, 101, 102);
insert into "Repetition" (id, entry_id, tag_id) values (3, 102, 101);

