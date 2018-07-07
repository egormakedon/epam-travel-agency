package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.repository.HotelRepository;
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
 * Repository class for {@link Hotel} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainDatabaseConfiguration} class,
 * implements {@link HotelRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("databaseRepository")

public class HotelDatabaseRepository implements HotelRepository {

    private static final String HOTEL_ID = "hotelId";
    private static final String HOTEL_NAME = "hotelName";
    private static final String HOTEL_PHONE = "hotelPhone";
    private static final String HOTEL_STARS = "hotelStars";

    @Autowired
    private DataSource dataSource;
    private Mapper mapper = new Mapper();

    /**
     * default constructor
     */
    public HotelDatabaseRepository() {}

    /**
     * Add operation
     *
     * @param hotel {@link Hotel}
     * @return true/false
     */
    @Override
    public boolean add(Hotel hotel) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_HOTEL = "INSERT " +
                "INTO hotel (hotel_name, hotel_phone, hotel_stars) " +
                "VALUES(:hotelName,:hotelPhone,:hotelStars)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(HOTEL_NAME, hotel.getName());
        parameters.put(HOTEL_PHONE, hotel.getPhone());
        parameters.put(HOTEL_STARS, hotel.getStars());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_HOTEL, parameters);
        return r == 1;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Hotel> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_HOTEL_BY_ID = "SELECT hotel_id hotelId, hotel_name hotelName, " +
                "hotel_phone hotelPhone, hotel_stars hotelStars " +
                "FROM hotel " +
                "WHERE hotel_id=:hotelId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(HOTEL_ID, id);
        return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_HOTEL_BY_ID, parameters, mapper));
    }

    /**
     * Remove operation
     *
     * @param hotel {@link Hotel}
     * @return true/false
     */
    @Override
    public boolean remove(Hotel hotel) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_HOTEL_BY_ID = "DELETE " +
                "FROM hotel " +
                "WHERE hotel_id=:hotelId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(HOTEL_ID, hotel.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_HOTEL_BY_ID, parameters);
        return r == 1;
    }

    /**
     * Update operation
     *
     * @param hotel {@link Hotel}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_HOTEL_BY_ID = "UPDATE hotel " +
                "SET hotel_name=:hotelName,hotel_phone=:hotelPhone," +
                "hotel_stars=:hotelStars " +
                "WHERE hotel_id=:hotelId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(HOTEL_ID, hotel.getId());
        parameters.put(HOTEL_NAME, hotel.getName());
        parameters.put(HOTEL_PHONE, hotel.getPhone());
        parameters.put(HOTEL_STARS, hotel.getStars());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_HOTEL_BY_ID, parameters);

        if (r == 1) {
            return Optional.of(hotel);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Hotel> {

        @Override
        public Hotel mapRow(ResultSet rs, int i) throws SQLException {
            Hotel hotel = new Hotel();
            hotel.setId(rs.getLong(HOTEL_ID));
            hotel.setName(rs.getString(HOTEL_NAME));
            hotel.setPhone(rs.getString(HOTEL_PHONE));
            hotel.setStars(rs.getByte(HOTEL_STARS));
            return hotel;
        }
    }
}