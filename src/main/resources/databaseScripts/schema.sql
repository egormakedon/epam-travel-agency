CREATE TABLE country
(
    country_id SERIAL PRIMARY KEY NOT NULL,
    country_name VARCHAR(100) NOT NULL,
    country_version INTEGER
);
CREATE UNIQUE INDEX country_country_name_uindex ON country (country_name);
COMMENT ON TABLE country IS 'Keep id and name about countries';

CREATE TABLE tour_type
(
    tour_type_id SERIAL PRIMARY KEY NOT NULL,
    tour_type_name VARCHAR(100) NOT NULL,
    tour_type_version INTEGER
);
CREATE UNIQUE INDEX tour_type_tour_type_name_uindex ON tour_type (tour_type_name);
COMMENT ON TABLE tour_type IS 'Keep id and name about tour types';

CREATE TABLE hotel
(
    hotel_id SERIAL PRIMARY KEY NOT NULL,
    hotel_name VARCHAR(100) NOT NULL,
    hotel_phone VARCHAR(25) NOT NULL,
    hotel_stars SMALLINT NOT NULL,
    hotel_version INTEGER
);
COMMENT ON TABLE hotel IS 'Keep info about hotels';

CREATE TABLE tour
(
    tour_id SERIAL PRIMARY KEY NOT NULL,
    tour_photo VARCHAR(100) NOT NULL,
    tour_date DATE NOT NULL,
    tour_duration INTEGER NOT NULL,
    fk_country_id INTEGER NOT NULL,
    fk_hotel_id INTEGER NOT NULL,
    fk_tour_type_id INTEGER NOT NULL,
    tour_description TEXT NOT NULL,
    tour_cost DECIMAL NOT NULL,
    tour_version INTEGER,
    CONSTRAINT fk_tour_country_id FOREIGN KEY (fk_country_id) REFERENCES country  (country_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tour_hotel_id FOREIGN KEY (fk_hotel_id) REFERENCES hotel  (hotel_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tout_tour_type_id FOREIGN KEY (fk_tour_type_id) REFERENCES tour_type  (tour_type_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE tour IS 'Keep info about tours';

CREATE TABLE user
(
    user_id SERIAL PRIMARY KEY NOT NULL,
    user_login VARCHAR(20) NOT NULL,
    user_password VARCHAR (20) NOT NULL,
    user_version INTEGER
);
CREATE UNIQUE INDEX user_user_login_uindex ON user (user_login);
COMMENT ON TABLE user IS 'Keep info about users';

CREATE TABLE review
(
    review_id SERIAL PRIMARY KEY NOT NULL,
    fk_tour_id INTEGER NOT NULL,
    fk_user_id INTEGER NOT NULL,
    review_content TEXT NOT NULL,
    review_version INTEGER,
    CONSTRAINT fk_review_tour_id FOREIGN KEY (fk_tour_id) REFERENCES tour (tour_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_review_user_id FOREIGN KEY (fk_user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE review IS 'Keep info about reviews';

CREATE TABLE user_tour
(
  fk_user_id INTEGER NOT NULL,
  fk_tour_id INTEGER NOT NULL,
  user_tour_version INTEGER,
  PRIMARY KEY (fk_user_id,fk_tour_id),
  CONSTRAINT user_tour_user_user_id_fk FOREIGN KEY (fk_user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT user_tour_tour_tour_id_fk FOREIGN KEY (fk_tour_id) REFERENCES tour (tour_id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE user_tour IS 'Connecting users with tours, that they attended';