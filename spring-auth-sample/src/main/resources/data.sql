insert into users (id, username, password) values ('95731b40-087d-41c3-8419-e811f22904c8', 'admin', '$2a$10$jNUYQtpjnfIRmfm3f7YMJ.pVD4T4BOGwnWGdxFzyYrygvwO0w0YKG');
insert into users (id, username, password) values ('29ef16d0-78e5-4fc4-a785-97023c77d2d6', 'user', '$2a$10$jNUYQtpjnfIRmfm3f7YMJ.pVD4T4BOGwnWGdxFzyYrygvwO0w0YKG');

insert into user_authority (id, name, user_id) values ('8c32f512-183c-44e5-905b-6643c6d55f5b', 'ROLE_ADMIN', '95731b40-087d-41c3-8419-e811f22904c8');
insert into user_authority (id, name, user_id) values ('d4681428-4d1f-4881-a368-f0fb1d681648', 'ROLE_USER', '29ef16d0-78e5-4fc4-a785-97023c77d2d6');
