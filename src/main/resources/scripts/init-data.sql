INSERT INTO travel_agency.country(country_name) VALUES ('BELARUS'),('RUSSIA'),('POLAND'),
    ('SPAIN'),('ENGLAND'),('UKRAINE'),('USA'),('CHINA');

INSERT INTO travel_agency.tour_type(tour_type_name) VALUES ('CHILDREN'),('WEEKEND'),
    ('WEDDING'),('SHOPING'),('EXCURSION');

INSERT INTO travel_agency.user(user_login, user_password) VALUES ('user1',crypt('user1', gen_salt('md5')));
INSERT INTO travel_agency.user(user_login, user_password) VALUES ('user2',crypt('user2', gen_salt('md5')));
INSERT INTO travel_agency.user(user_login, user_password) VALUES ('user3',crypt('user3', gen_salt('md5')));
INSERT INTO travel_agency.user(user_login, user_password) VALUES ('user4',crypt('user4', gen_salt('md5')));
INSERT INTO travel_agency.user(user_login, user_password) VALUES ('user5',crypt('user5', gen_salt('md5')));


INSERT INTO travel_agency.hotel(hotel_name, hotel_phone, hotel_stars, fk_country_id)
    VALUES ('hotel1','1234567',5,1),('hotel2','12345678910',3,2),('hotel3','291234',2,3);

INSERT INTO travel_agency.tour(tour_photo, tour_date, tour_duration, tour_description, tour_cost, fk_country_id, fk_hotel_id, fk_tour_type_id)
    VALUES ('/img/tour1.jpg','2018-04-01', '01:00:00', 'the best tour', 100, 4, 1, 3),
    ('/img/tour2.jpg','2018-03-25', '72:30:00', 'second tour', 500, 6, 3, 5);

INSERT INTO travel_agency.review(fk_tour_id, fk_user_id, review_content)
    VALUES(1, 1, 'it was the best tour'),(1,2,'average'),(2,4,'the worst tour ever'),(2,5,'very bad for mach cost');

INSERT INTO travel_agency.user_tour(fk_user_id, fk_tour_id)
    VALUES (1, 1),(2, 1),(4, 2),(5, 2),(3,1),(3,2);

