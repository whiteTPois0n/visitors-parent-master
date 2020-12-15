insert into role (id, role_name) values (1, 'ROLE_SUPERADMIN');
insert into role (id, role_name) values (2, 'ROLE_ADMIN');
insert into role (id, role_name) values (3, 'ROLE_HRSTAFF');



insert into user (id, username, password, enabled, role_id) values (1, 'superadmin', '$2y$12$XGRfnkQbBySpb.XP.k9gWOwDCtmLBEcHvO0a8ql44jhIJFdjWUaDG', 1, 1);
insert into user (id, username, password, enabled, role_id) values (2, 'hrstaff', '$2y$10$uNrUjUJHWiYYljbZN40.lOys3aaZ9EiVub0ImOJQg8xs92xyWtBA2', 1, 3);
insert into user (id, username, password, enabled, role_id) values (3, 'admin', '$2y$12$xRwgnXLVoY6Kdw08AZPP.u2W1Ly1vsTZXnp6IhFM3dNsaUD82IOr2', 1, 2);

--visitor
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('MR', 'Jack', 'Sparrow', 'jacksp@gmail.com', '66666666', '78977 Pirate Street', 'PirateInc');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('MS', 'Saunderson', 'Dailly', 'sdailly1@gmail.com', '77777777', '98725 Luster Street', 'YombuOrg');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('MR', 'Jack', 'Black', 'jack_black@gmail.com', '88888888', '82977 Nuby Nubs Street', 'JackCorp');
