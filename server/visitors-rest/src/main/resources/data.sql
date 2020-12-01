insert into role (id, role_name) values (1, 'ROLE_SUPERADMIN');
insert into role (id, role_name) values (2, 'ROLE_ADMIN');
insert into role (id, role_name) values (3, 'ROLE_HRSTAFF');



insert into user (id, username, password, enabled, role_id) values (1, 'superadmin', '$2y$12$XGRfnkQbBySpb.XP.k9gWOwDCtmLBEcHvO0a8ql44jhIJFdjWUaDG', 1, 1);
insert into user (id, username, password, enabled, role_id) values (2, 'hrstaff', '$2y$10$uNrUjUJHWiYYljbZN40.lOys3aaZ9EiVub0ImOJQg8xs92xyWtBA2', 1, 3);
insert into user (id, username, password, enabled, role_id) values (3, 'admin', '$2y$12$xRwgnXLVoY6Kdw08AZPP.u2W1Ly1vsTZXnp6IhFM3dNsaUD82IOr2', 1, 2);

insert into visitor_type values(1, 'Customer');
insert into visitor_type values(2, 'Partner');
insert into visitor_type values(3, 'Candidate');
insert into visitor_type values(4, 'Provider');
--insert into visitor_type values(5, 'Other');

insert into factory values(1, 'Java factory');
insert into factory values(2, 'DotNet factory');

insert into meeting_type values(1, 'Aptitude Test');
insert into meeting_type values(2, 'Interview');
insert into meeting_type values(3, 'Contract');

--other visits
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Jack', 'Sparrow', 2, 'jacksp@gmail.com', '99999999', '78977 Pirate Street', 'PirateInc', 'Kenny', 'Reason 1', 39.1, '2020-04-12 19:55:01', '0020184449 515,66337', TRUE);
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MS', 'Saunderson', 'Dailly', 1, 'sdailly1@gmail.com', '77777777', '98725 Luster Street', 'YombuOrg', 'Olivier', 'Reason 2', 38.7, '2020-10-13 15:02:25', '0050684412 154,24349', TRUE);
--visits
--sponge bob today(not yet)
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Sponge', 'Bob', 1, 'spongeBob@gmail.com', '66666666', '67587 Sponge Street', 'SpongeInc', 'Kenny', 'Reason 5', 40.1, '2020-11-26 22:00:00', '0010184419 155,26339', TRUE);
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Sponge', 'Bob', 1, 'spongeBob@gmail.com', '66666666', '67587 Sponge Street', 'SpongeInc', 'Kenny', 'Reason 3', 40.1, '2020-11-26 20:00:00', '0010184419 155,26339', FALSE);
--sponge bob past
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Sponge', 'Bob', 1, 'spongeBob@gmail.com', '66666666', '67587 Sponge Street', 'SpongeInc', 'Kenny', 'Reason 3', 40.1, '2020-11-21 06:00:00', '0010184419 155,26339', TRUE);
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Sponge', 'Bob', 1, 'spongeBob@gmail.com', '66666666', '67587 Sponge Street', 'SpongeInc', 'Kenny', 'Reason 5', 40.1, '2020-10-20 10:00:00', '0010184419 155,26339', TRUE);

--sponge bob future
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Sponge', 'Bob', 1, 'spongeBob@gmail.com', '66666666', '67587 Sponge Street', 'SpongeInc', 'Kenny', 'Reason 5', 40.1, '2020-11-27 13:00:00', '0010184419 155,26339', TRUE);

--interviews
--sponge bob future
insert into organiser (first_name, last_name, job_title, factory_id, contact_person, contact_person_visa, date_time, meeting_room, meeting_type_id) values ('Sponge', 'Bob', 'Trainee Software Engineer', 1, 'Nowbuth Abhimanyu', 'abh', '2020-11-28 23:00:00', 'mu-room-515', 1);
insert into organiser (first_name, last_name, job_title, factory_id, contact_person, contact_person_visa, date_time, meeting_room, meeting_type_id) values ('Sponge', 'Bob', 'Trainee Software Engineer', 2, 'Nowbuth Abhimanyu', 'abh', '2020-11-28 09:00:00', 'mu-room-515', 1);
insert into organiser (first_name, last_name, job_title, factory_id, contact_person, contact_person_visa, date_time, meeting_room, meeting_type_id) values ('Sponge', 'Bob', 'Trainee Software Engineer', 2, 'Nowbuth Abhimanyu', 'abh', '2020-11-27 05:00:00', 'mu-room-515', 1);

--insert into organiser (first_name, last_name, job_title, factory_id, contact_person, contact_person_visa, date_time, meeting_room, meeting_type_id) values ('Sponge', 'Bob', 'Trainee Software Engineer', 3, 'Nowbuth Abhimanyu', 'abh', '2020-11-27 13:00:00', 'mu-room-515', 1);