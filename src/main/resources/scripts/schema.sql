CREATE SCHEMA IF NOT EXISTS travel_agency;

CREATE TABLE IF NOT EXISTS travel_agency.country
(
    country_id SERIAL PRIMARY KEY NOT NULL,
    country_name VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX country_country_name_uindex ON travel_agency.country (country_name);
COMMENT ON TABLE travel_agency.country IS 'Keep info about countries.';

CREATE TABLE IF NOT EXISTS travel_agency.tour_type
(
    tour_type_id SERIAL PRIMARY KEY NOT NULL,
    tour_type_name VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX tour_type_tour_type_name_uindex ON travel_agency.tour_type (tour_type_name);
COMMENT ON TABLE travel_agency.tour_type IS 'Keep different tour types.';

CREATE TABLE IF NOT EXISTS travel_agency.hotel
(
    hotel_id SERIAL PRIMARY KEY NOT NULL,
    hotel_name VARCHAR(100) NOT NULL,
    hotel_phone VARCHAR(20) NOT NULL,
    fk_country_id SERIAL NOT NULL,
    hotel_stars SMALLINT NOT NULL,
    CONSTRAINT fk_hotel_country_id FOREIGN KEY (fk_country_id) REFERENCES "travel_agency".country (country_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE travel_agency.hotel IS 'Keep info about hotel.';

CREATE TABLE IF NOT EXISTS travel_agency.tour
(
    tour_id SERIAL PRIMARY KEY NOT NULL,
    tour_photo BYTEA NOT NULL,
    tour_date DATE NOT NULL,
    tour_duration INTERVAL NOT NULL,
    fk_country_id SERIAL NOT NULL,
    fk_hotel_id SERIAL NOT NULL,
    fk_tour_type_id SERIAL NOT NULL,
    tour_description TEXT NOT NULL,
    tour_cost MONEY NOT NULL,
    CONSTRAINT fk_tour_country_id FOREIGN KEY (fk_country_id) REFERENCES "travel_agency".country  (country_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tour_hotel_id FOREIGN KEY (fk_hotel_id) REFERENCES "travel_agency".hotel  (hotel_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tout_tour_type_id FOREIGN KEY (fk_tour_type_id) REFERENCES "travel_agency".tour_type  (tour_type_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE travel_agency.tour IS 'Keep info about tours.';

CREATE TABLE IF NOT EXISTS travel_agency."user"
(
    user_id SERIAL PRIMARY KEY NOT NULL,
    user_login VARCHAR(16) NOT NULL,
    user_password CHAR(40) NOT NULL
);
CREATE UNIQUE INDEX user_user_login_uindex ON travel_agency."user" (user_login);
COMMENT ON TABLE travel_agency."user" IS 'Keep user info.';

CREATE TABLE IF NOT EXISTS travel_agency.review
(
    review_id SERIAL PRIMARY KEY NOT NULL,
    fk_tour_id SERIAL NOT NULL,
    fk_user_id SERIAL NOT NULL,
    review_content TEXT NOT NULL,
    CONSTRAINT fk_review_tour_id FOREIGN KEY (fk_tour_id) REFERENCES "travel_agency".tour (tour_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_review_user_id FOREIGN KEY (fk_user_id) REFERENCES "travel_agency"."user" (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE travel_agency.review IS 'Keep info about review.';

CREATE TABLE IF NOT EXISTS travel_agency.tour_review
(
    id SERIAL PRIMARY KEY NOT NULL,
    fk_tour_id SERIAL NOT NULL,
    fk_review_id SERIAL NOT NULL,
    CONSTRAINT fk_dep_tour_id FOREIGN KEY (fk_tour_id) REFERENCES "travel_agency".tour (tour_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_dep_review_id FOREIGN KEY (fk_review_id) REFERENCES "travel_agency".review (review_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE travel_agency.tour_review IS 'Keep tour review dependency.';