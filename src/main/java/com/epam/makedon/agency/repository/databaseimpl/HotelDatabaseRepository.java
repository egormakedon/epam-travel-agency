package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class HotelDatabaseRepository implements HotelRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 2.0
 */
@Repository
@Profile("databaseRepository")
public class HotelDatabaseRepository implements HotelRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelDatabaseRepository.class);
    private Mapper mapper = new Mapper();

    @Autowired
    @Setter
    private DataSource dataSource;

    public HotelDatabaseRepository() {}

    /**
     * @param hotel object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Hotel hotel) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_HOTEL = "INSERT INTO hotel (hotel_name, hotel_phone, hotel_stars) VALUES(:hotelName,:hotelPhone,:hotelStars)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelName", hotel.getName());
        parameters.put("hotelPhone", hotel.getPhone());
        parameters.put("hotelStars", hotel.getStars());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_HOTEL, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find hotel object in repository
     * @return object, wrapped in optional, result of finding, cause null
     */
    @Override
    public Optional<Hotel> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_HOTEL_BY_ID = "SELECT hotel_id id, hotel_name name, hotel_phone phone, hotel_stars stars FROM hotel WHERE hotel_id=:hotelId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_HOTEL_BY_ID, parameters, mapper));
    }

    /**
     * @param hotel objectm which be removing from repository
     * @return boolean result of removing
     */
    @Override
    public boolean remove(Hotel hotel) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_HOTEL_BY_ID = "DELETE FROM hotel WHERE hotel_id=:hotelId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelId", hotel.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_HOTEL_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param hotel object, which be updating in repository
     * @return object, wrapped in optionalm result of updating, cause be null
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_HOTEL_BY_ID = "UPDATE hotel SET hotel_name=:hotelName,hotel_phone=:hotelPhone,hotel_stars=:hotelStars WHERE hotel_id=:hotelId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelName", hotel.getName());
        parameters.put("hotelPhone", hotel.getPhone());
        parameters.put("hotelStars", hotel.getStars());
        parameters.put("hotelId", hotel.getId());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_HOTEL_BY_ID, parameters);

        if (r == 1) {
            return Optional.ofNullable(hotel);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Hotel> {
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String PHONE = "phone";
        private static final String STARS = "stars";

        @Override
        public Hotel mapRow(ResultSet rs, int i) throws SQLException {
            Hotel hotel = new Hotel();
            hotel.setId(rs.getLong(ID));
            hotel.setName(rs.getString(NAME));
            hotel.setPhone(rs.getString(PHONE));
            hotel.setStars(rs.getByte(STARS));
            return hotel;
        }
    }
}