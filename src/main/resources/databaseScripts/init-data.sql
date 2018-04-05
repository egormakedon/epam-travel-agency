INSERT INTO "country" (country_name) VALUES ('BELARUS'),('RUSSIA'),('POLAND'),
    ('SPAIN'),('ENGLAND'),('UKRAINE'),('USA'),('CHINA');

INSERT INTO "tour_type" (tour_type_name) VALUES ('CHILDREN'),('WEEKEND'),
    ('WEDDING'),('SHOPING'),('EXCURSION');

INSERT INTO "user" (user_login, user_password) VALUES ('user1','user1'),('user2','user2'),('user3','user3'),
    ('user4','user4'),('user5','user5');

INSERT INTO "hotel" (hotel_name, hotel_phone, hotel_stars)
    VALUES ('hotel1','1234567',5),('hotel2','12345678910',3),('hotel3','291234',2);

INSERT INTO "tour" (tour_photo, tour_date, tour_duration, tour_description, tour_cost, fk_country_id, fk_hotel_id, fk_tour_type_id)
    VALUES ('null','2018-04-01', '10', 'the best tour', 100, 4, 1, 3),
    ('null','2018-03-25', '15', 'second tour', 500, 6, 3, 5);

INSERT INTO "review" (fk_tour_id, fk_user_id, review_content)
    VALUES(1, 1, 'it was the best tour'),(1,2,'average'),(2,4,'the worst tour ever'),(2,5,'very bad for mach cost');

INSERT INTO "user_tour" (fk_user_id, fk_tour_id)
    VALUES (1, 1),(2, 1),(4, 2),(5, 2),(3,1),(3,2);