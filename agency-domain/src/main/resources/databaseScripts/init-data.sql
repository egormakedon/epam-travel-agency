INSERT INTO country (country_name, country_version) VALUES
    ('BELARUS', 0),
    ('RUSSIA', 0),
    ('POLAND', 0),
    ('SPAIN', 0),
    ('ENGLAND', 0),
    ('UKRAINE', 0),
    ('USA', 0);

INSERT INTO tour_type (tour_type_name, tour_type_version) VALUES
    ('CHILDREN', 0),
    ('WEEKEND', 0),
    ('WEDDING', 0),
    ('SHOPING', 0);

INSERT INTO user (user_login, user_password, role_id, user_version) VALUES
    ('user1', 'user1', 2, 0),
    ('user2', 'user2', 1, 0),
    ('user3', 'user3', 1, 0),
    ('user4', 'user4', 1, 0),
    ('user5', 'user5', 1, 0);

INSERT INTO hotel (hotel_name, hotel_phone, hotel_stars, hotel_version) VALUES
    ('hotel1', '1234567', 5, 0),
    ('hotel2', '910', 3, 0),
    ('hotel3', '234', 1, 0),
    ('hotel4', '2934', 4, 0),
    ('hotel5', '561834', 2, 0);

INSERT INTO tour (tour_photo, tour_date, tour_duration, tour_description, tour_cost,
    fk_country_id, fk_hotel_id, fk_tour_type_id, tour_version) VALUES
    ('photo1', '2018-01-25', '5', 'the best tour 1', 100, 1, 1, 1, 0),
    ('photo2', '2018-06-10', '14', 'the best tour 2', 243, 2, 1, 2, 0),
    ('photo3', '2018-11-09', '3', 'the best tour 3', 57, 3, 1, 1, 0),
    ('photo4', '2018-09-11', '6', 'the best tour 4', 121, 4, 1, 2, 0),
    ('photo5', '2018-05-28', '21', 'second tour 5', 2000, 6, 3, 3, 0);

INSERT INTO review (fk_tour_id, fk_user_id, review_content, review_version) VALUES
    (1, 1, 'it was the best tour', 0),
    (1, 1, 'it was the best tour', 0),
    (2, 2, 'average', 0),
    (3, 4, 'the worst tour ever', 0),
    (4, 4, 'the worst tour ever', 0),
    (5, 5, 'very bad for mach cost', 0),
    (2, 5, 'very bad for mach cost', 0);

INSERT INTO user_tour (fk_user_id, fk_tour_id, user_tour_version) VALUES
    (1, 1, 0),
    (2, 2, 0),
    (4, 3, 0),
    (4, 4, 0),
    (5, 5, 0),
    (5, 2, 0),
    (1, 3, 0),
    (1, 4, 0),
    (3, 5, 0),
    (3, 2, 0);