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

insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Jack', 'Sparrow', 2, 'jacksp@gmail.com', '99999999', '78977 Pirate Street', 'PirateInc', 'Kenny', 'Reason 1', 39.1, '2020-04-12 19:55:01', '0020184449 515,66337', TRUE);
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MS', 'Saunderson', 'Dailly', 1, 'sdailly1@gmail.com', '77777777', '98725 Luster Street', 'YombuOrg', 'Olivier', 'Reason 2', 38.7, '2020-10-13 15:02:25', '0050684412 154,24349', TRUE);
insert into visitor (title, first_name, last_name, visitor_type_id, email, phone_number, address, organization, contact_person, reason_of_visit, temperature, checked_in_time, badge_number, status) values ('MR', 'Sponge', 'Bob', 1, 'spongeBob@gmail.com', '66666666', '67587 Sponge Street', 'SpongeInc', 'Kenny', 'Reason 3', 40.1, '2020-10-14 06:12:25', '0010184419 155,26339', TRUE);

insert into organiser (first_name, last_name, job_title, factory_id, contact_person, contact_person_visa, date_time, meeting_room, meeting_type_id) values ('Sponge', 'Bob', 'Trainee Software Engineer', 1, 'Nowbuth Abhimanyu', 'abh', '2020-10-14 06:12:25', 'mu-room-515', 1);