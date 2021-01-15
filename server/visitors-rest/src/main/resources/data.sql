--role tbl data
insert into role (id, role_name) values (1, 'SUPERADMIN');
insert into role (id, role_name) values (2, 'ADMIN');
insert into role (id, role_name) values (3, 'HRSTAFF');


--user tbl data
insert into user (id, username, password, enabled, role_id) values (1, 'superadmin', '$2y$12$XGRfnkQbBySpb.XP.k9gWOwDCtmLBEcHvO0a8ql44jhIJFdjWUaDG', 1, 1);
insert into user (id, username, password, enabled, role_id) values (2, 'hrstaff', '$2y$10$uNrUjUJHWiYYljbZN40.lOys3aaZ9EiVub0ImOJQg8xs92xyWtBA2', 1, 3);
insert into user (id, username, password, enabled, role_id) values (3, 'admin', '$2y$12$xRwgnXLVoY6Kdw08AZPP.u2W1Ly1vsTZXnp6IhFM3dNsaUD82IOr2', 1, 2);
insert into user (id, username, password, enabled, role_id) values (4, 'Azhar', '$2y$12$tsMXnCycYpBZGGh1oW2a0.JYxqMZGkpNE/FWf1KFRoaetSRQNA1Qq', 1, 2);



--contact tbl data
insert into contact (id, first_name, last_name, visa, phone_number) values (1, 'Awad' , 'Luckhoo' , 'AWL', '57444444');
insert into contact (id, first_name, last_name, visa, phone_number) values (2, 'Kenny', 'Kian Fat', 'WKK', '57725970');


--visitor tbl data
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Jack', 'Sparrow', 'jacksp@gmail.com', '78847562', '78977 Pirate Street', 'PirateInc');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Saunderson', 'Dailly', 'sdailly1@gmail.com', '72572222', '98725 Luster Street', 'YombuOrg');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Jack', 'Black', 'jack_black@gmail.com', '89475487', '82977 Nuby Nubs Street', 'JackCorp');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Jackie', 'Chan', 'jack_black@gmail.com', '45457882', '926 Indo Street', 'Fivechat');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Jack', 'Reaper', 'jack_reaps@gmail.com', '96446787', '8392 Malesuada Street', 'Feedfire');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'James', 'Lebron', 'james_lebron@gmail.com', '47759515', '1153 Tellus Avenue', 'Realbridge');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'James', 'Bond', 'jamesBond@gmail.com', '78552158', '1758 Lobortis Street', 'Blogpad');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'James', 'Bourn', 'james_bourn@gmail.com', '86978455', '4520 Ipsum Street', 'Pixonyx');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'James', 'Arthur', 'james_arthur7@gmail.com', '48992354', '9413 Inceptos Street', 'Quimba');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'James', 'Charles', 'james_charles_gurl@gmail.com', '98589877', '2214 Mauris Street', 'Edgelab');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Norina', 'Forten', 'nforten0@yahoo.co.jp', '92918334', '67 Caliangt Trail', 'InnoZ');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Sigismondo', 'Groundwator', 'sgroundwator1@weather.com', '88583806', '10 Waxwing Pass', 'Skinder');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Kinnie', 'Springle', 'kspringle2@amazon.co.jp', '88232913', '28 Kensington Court', 'Blognation');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Kelsey', 'Schutt', 'kschutt3@jugem.jp', '35442538', '95645 Toban Street', 'Camido');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Chaddie', 'Southwood', 'csouthwood4@ehow.com', '71835373', '4 Drewry Junction', 'Eabox');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Russ', 'Aldine', 'raldine5@mtv.com', '14470281', '666 Lerdahl Way', 'Plambee');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Demetra', 'Featherstone', 'dfeatherstone6@discuz.net', '53590681', '5 Fordem Junction', 'Yodel');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Anatollo', 'Mates', 'amates7@ebay.com', '49387905', '1339 Eastwood Center', 'Quire');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Rafaelia', 'Benduhn', 'rbenduhn8@prweb.com', '45379513', '31268 Barnett Center', 'Realbuzz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Grata', 'Smidmoor', 'gsmidmoor9@ehow.com', '58986680', '71943 Utah Pass', 'Kanoodle');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Ariana', 'Kilner', 'akilnera@feedburner.com', '78874094', '811 Lake View Pass', 'Skimia');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Demetria', 'Jolin', 'djolinb@google.it', '68531922', '3541 Twin Pines Parkway', 'Mydo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Davina', 'Curreen', 'dcurreenc@weather.com', '50225971', '26933 Express Hill', 'Aimbu');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Monika', 'Geely', 'mgeelyd@buzzfeed.com', '59237415', '72 Hallows Terrace', 'Plambee');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Darren', 'Bradane', 'dbradanee@bravesites.com', '80781643', '28344 School Point', 'Yotz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Phillip', 'McGow', 'pmcgowf@geocities.jp', '18634369', '15 Eastwood Park', 'Geba');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Sam', 'Tomaszewski', 'stomaszewskig@wikimedia.org', '86891114', '1 Heath Point', 'Topicware');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Chas', 'Forgan', 'cforganh@is.gd', '21155754', '71028 Rusk Parkway', 'Twitternation');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Nahum', 'Jolliman', 'njollimani@issuu.com', '82318398', '647 Prentice Avenue', 'Oodoo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Morgana', 'Rahl', 'mrahlj@quantcast.com', '85737611', '98105 Karstens Pass', 'Abatz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Hartwell', 'Yatman', 'hyatmank@wp.com', '84355471', '30681 Farragut Street', 'Kazu');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Belva', 'Huffy', 'bhuffyl@yellowpages.com', '54252608', '37 Kennedy Drive', 'Yodel');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Renado', 'Craigg', 'rcraiggm@rambler.ru', '21466992', '06005 Londonderry Pass', 'Zooveo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Bobinette', 'Marklew', 'bmarklewn@fda.gov', '53349391', '75263 Cardinal Alley', 'Mynte');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Chic', 'Camplejohn', 'ccamplejohno@bbb.org', '98210484', '80075 Beilfuss Street', 'Vitz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Ethelyn', 'Bertelmot', 'ebertelmotp@bravesites.com', '32128264', '84 Mitchell Pass', 'Eayo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Hedi', 'Bedwell', 'hbedwellq@storify.com', '95261788', '747 Darwin Junction', 'Quamba');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Lorne', 'Blasio', 'lblasior@newyorker.com', '88164603', '60 Northfield Court', 'Oyonder');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Berny', 'Umbert', 'bumberts@tinyurl.com', '75128294', '1 Haas Avenue', 'Mybuzz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Baily', 'Cardenas', 'bcardenast@google.com.hk', '94547830', '7 Pierstorff Park', 'Cogidoo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Pennie', 'Bodill', 'pbodillu@mit.edu', '51112790', '82 Del Sol Hill', 'Meejo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Torey', 'Castagno', 'tcastagnov@ft.com', '72549241', '228 Nancy Lane', 'Vinder');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Lauralee', 'Ameer-Beg', 'lameerbegw@toplist.cz', '16263259', '5687 Sachtjen Center', 'Twitterbridge');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Woodrow', 'Cawt', 'wcawtx@4shared.com', '36711557', '6764 Eagan Drive', 'Janyx');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Zora', 'Latter', 'zlattery@list-manage.com', '50781751', '9159 Butternut Circle', 'Voonix');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Sibyl', 'Heading', 'sheadingz@odnoklassniki.ru', '98347361', '01365 Merrick Way', 'Latz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Gianna', 'MacSharry', 'gmacsharry10@liveinternet.ru', '28844252', '7719 Sachs Pass', 'Twimm');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Maynard', 'Quenell', 'mquenell11@nytimes.com', '97779021', '6 Lien Junction', 'Realbridge');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Zelma', 'Schoales', 'zschoales12@vk.com', '19659279', '55 American Trail', 'Ozu');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Bartolemo', 'Tantum', 'btantum13@weebly.com', '33153344', '4 Oriole Court', 'Yabox');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Matteo', 'Francklin', 'mfrancklin14@webnode.com', '28425972', '803 Sycamore Drive', 'Plajo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Packston', 'Ciotti', 'pciotti15@drupal.org', '75876908', '1 Merry Point', 'Rhyzio');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Nikolas', 'Banbury', 'nbanbury16@stumbleupon.com', '68284363', '8451 High Crossing Terrace', 'Flipstorm');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Pierre', 'Van Son', 'pvanson17@cbc.ca', '56250968', '30136 Hagan Avenue', 'Centimia');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Foss', 'Lopes', 'flopes18@imdb.com', '10412876', '75567 Pepper Wood Junction', 'Twinder');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Emmaline', 'Layland', 'elayland19@vkontakte.ru', '26672612', '2 Maple Court', 'Kaymbo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Rockwell', 'Lambdin', 'rlambdin1a@army.mil', '78242218', '9924 Old Gate Trail', 'Quatz');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Ethelda', 'Penzer', 'epenzer1b@webeden.co.uk', '28467222', '84946 Scoville Hill', 'Fivespan');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Keir', 'Stansell', 'kstansell1c@bloglovin.com', '97280663', '5413 Harbort Hill', 'Jayo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Golda', 'Villiers', 'gvilliers1d@ifeng.com', '19747879', '9 Mosinee Drive', 'Geba');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Toiboid', 'Balshaw', 'tbalshaw1e@latimes.com', '86593197', '87845 Golf View Alley', 'Flashset');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Gabriel', 'Thomlinson', 'gthomlinson1f@a8.net', '34763661', '7 Troy Center', 'Rhybox');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Laural', 'Thow', 'lthow1g@ning.com', '40269034', '33 Oak Valley Avenue', 'Vipe');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Adams', 'Crozier', 'acrozier1h@telegraph.co.uk', '50922191', '5 Pepper Wood Hill', 'Centidel');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Griff', 'Bernaciak', 'gbernaciak1i@ihg.com', '78861822', '2342 Fulton Circle', 'Meemm');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Luz', 'Mattys', 'lmattys1j@addthis.com', '16481683', '605 Northport Parkway', 'Zoomzone');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mr', 'Artemus', 'Giral', 'agiral1k@bbc.co.uk', '31449893', '47 Fuller Plaza', 'Voonder');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Ms', 'Dione', 'Criple', 'dcriple1l@g.co', '98678495', '94391 Hoepker Parkway', 'Aimbo');
insert into visitor (title, first_name, last_name, email, phone_number, address, organization) values ('Mrs', 'Ellie', 'Snadden', 'esnadden1m@samsung.com', '26611544', '81 Hayes Alley', 'Skilith');


-- appointment tbl data (PAST APPOINTMENTS)
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (1, 'Junior Software Engineer', 'JAVA', 'INTERVIEW', 'mu-room-515', '2020-01-11 11:00:00', 2, 13);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (2, 'Senior Software Engineer', 'JAVA', 'APTITUDE_TEST', 'mu-room-520', '2020-01-12 12:00:00', 2, 26);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (3, 'Project Manager', 'DOTNET',  'INTERVIEW', 'mu-room-518', '2020-01-11 14:00:00', 1, 38);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (4, 'Junior Software Engineer', 'DOTNET', 'APTITUDE_TEST', 'mu-room-515', '2020-01-15 12:30:00', 1, 15);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (5, 'Associate Architect', 'JAVA', 'INTERVIEW', 'mu-room-518', '2020-01-26 13:30:00', 2, 65);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (6, 'Associate Software Engineer', 'JAVA', 'APTITUDE_TEST', 'mu-room-512', '2020-02-28 11:00:00', 2, 39);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (7, 'Architect', 'JAVA', 'INTERVIEW', 'mu-room-515', '2020-02-02 10:30:00', 1, 26);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (8, 'Associate Software Engineer', 'DOTNET','APTITUDE_TEST', 'mu-room-515', '2020-01-15 12:30:00', 1, 35);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (9, 'Project Manager', 'JAVA', 'CONTRACT', 'mu-room-521', '2020-02-18 11:30:00', 1, 26);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (10, 'Architect', 'JAVA', 'INTERVIEW', 'mu-room-516', '2020-02-27 12:00:00', 2, 8);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (11, 'Architect', 'DOTNET', 'APTITUDE_TEST', 'mu-room-517', '2020-02-23 11:00:00', 1, 5);
-- appointment tbl data (FUTURE VISITS)
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (12, 'Junior Software Engineer', 'JAVA', 'APTITUDE_TEST', 'mu-room-515', '2021-01-11 11:00:00', 2, 33);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (13, 'Junior Software Engineer', 'JAVA', 'APTITUDE_TEST', 'mu-room-520', '2021-01-12 12:00:00', 2, 6);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (14, 'Project Manager', 'DOTNET',  'INTERVIEW', 'mu-room-518', '2021-02-11 14:00:00', 1, 48);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (15, 'Junior Software Engineer', 'JAVA', 'APTITUDE_TEST', 'mu-room-515', '2021-01-15 12:30:00', 1, 17);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (16, 'Associate Architect', 'DOTNET', 'INTERVIEW', 'mu-room-518', '2021-02-26 13:30:00', 2, 5);
insert into appointment (id, job_title, factory, meeting_type, meeting_room, appointment_date, contact_id, visitor_id) values (17, 'Junior Software Engineer', 'JAVA', 'APTITUDE_TEST', 'mu-room-512', '2021-01-15 11:00:00', 2, 19);


--visit tbl data
--visits with an appointment only (x11 CANDIDATES) (APTITUDE TEST, JOB_INTERVIEWS, JOB_OFFERS) (PAST VISITS)
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (1, 'CANDIDATE', 'Aptitude test', '0021584900', 36.6, '2020-01-15 12:25:05', '2020-01-15 13:31:15', 35, 8, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (2, 'CANDIDATE', 'Aptitude test', '0027235790', 37.1, '2020-01-15 12:28:26', '2020-01-15 14:01:16', 15, 4, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (3, 'CANDIDATE', 'Aptitude test', '0028536025', 35.5, '2020-02-28 11:02:10', '2020-02-28 12:30:07', 39, 6, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (4, 'CANDIDATE', 'Job interview', '0022709265', 38.3, '2021-01-11 13:54:41', '2021-01-11 14:46:09', 38, 3, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (5, 'CANDIDATE', 'Job interview', '0025096999', 39.5, '2020-01-26 13:29:30', '2020-01-26 14:24:14', 65, 5, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (6, 'CANDIDATE', 'Aptitude test', '0021917021', 35.1, '2021-01-12 12:03:48', '2021-01-12 12:32:52', 26, 2, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (7, 'CANDIDATE', 'Job interview', '0022058284', 39.9, '2021-01-10 11:05:51', '2021-01-10 11:40:20', 13, 1, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (8, 'CANDIDATE', 'Job interview', '0021917021', 36.7, '2020-07-02 10:30:05', '2020-07-02 11:16:06', 26, 7, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (9, 'CANDIDATE', 'Job offer', '0021917021', 38.2, '2020-02-18 11:29:19', '2020-02-18 12:04:27', 26, 9, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (10, 'CANDIDATE', 'Interview', '0035584656', 36.7, '2020-02-27 12:16:01', '2020-02-27 13:02:10', 8, 10, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (11, 'CANDIDATE', 'Aptitude test', '0022058284', 39.9, '2020-02-23 11:01:25', '2020-02-23 12:33:13', 5, 11, null);

-- TODO modify these visits with a contact only (PAST VISITS)
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (12, 'CUSTOMER', 'Reason', '0022058284', 39.9, '2020-11-13 13:12:30', '2020-11-17 20:29:02', 17, null, 2);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (13, 'CUSTOMER', 'Reason', '0021917021', 35.1, '2020-11-26 15:38:46', '2020-10-05 21:14:03', 20, null, 1);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (14, 'PARTNER', 'Reason', '0035553675', 38.2, '2020-10-29 03:07:47', '2020-11-28 13:18:28', 44, null, 1);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (15, 'PARTNER', 'Reason', '0035584656', 36.7, '2020-02-12 01:41:34', '2020-02-11 12:19:24', 66, null, 2);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (16, 'PARTNER', 'Reason', '0022058284', 39.9, '2020-11-13 13:12:30', '2020-11-17 20:29:02', 11, null, 1);

--visits with neither an appointment nor contact (x1 PROVIDER) (Fruit delivery every monday)
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (18, 'PROVIDER', 'Fruit delivery', '0035766352', 37.7, '2020-03-30 10:03:44', '2020-03-30 10:23:22', 51, null, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (19, 'PROVIDER', 'Fruit delivery', '0035766352', 37.5, '2020-12-07 10:01:07', '2020-12-07 10:21:12', 51, null, null);
insert into visit (id, visitor_type, reason_of_visit, badge_number, temperature, checked_in_time, checked_out_time, visitor_id, appointment_id, contact_id) values (20, 'PROVIDER', 'Fruit delivery', '0035766352', 36.8, '2020-12-14 10:03:15', '2020-12-14 10:26:17', 51, null, null);