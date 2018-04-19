package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code HotelDatabaseRepository} implements {@code HotelRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
public class HotelDatabaseRepository implements com.epam.makedon.agency.repository.HotelRepository {
    private static final Logger LOGGER;
    private static HotelDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(HotelDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private static class Mapper implements RowMapper<Hotel> {
        private static final Mapper INSTANCE = new Mapper();
        private Mapper() {}
        public static Mapper getInstance() { return INSTANCE; }

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

    private static final String SQL_INSERT_HOTEL = "INSERT INTO hotel (hotel_name, hotel_phone, hotel_stars) VALUES(:hotelName,:hotelPhone,:hotelStars)";
    private static final String SQL_SELECT_HOTEL_BY_ID = "SELECT hotel_id id, hotel_name name, hotel_phone phone, hotel_stars stars FROM hotel WHERE hotel_id=:hotelId";
    private static final String SQL_DELETE_HOTEL_BY_ID = "DELETE FROM hotel WHERE hotel_id=:hotelId";
    private static final String SQL_UPDATE_HOTEL_BY_ID = "UPDATE hotel SET hotel_name=:hotelName,hotel_phone=:hotelPhone,hotel_stars=:hotelStars WHERE hotel_id=:hotelId";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private HotelDatabaseRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * @return Object
     * @throws CloneNotSupportedException when try cloning
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LOGGER.error("Tried to clone singleton");
        throw new CloneNotSupportedException("Tried to clone singleton");
    }

    /**
     * protection from serialization
     *
     * @return Object
     */
    protected Object readResolve() {
        return instance;
    }

    public static HotelDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new HotelDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param hotel object, which be insert into repository
     * @return boolean result
     */
    @Override
    public boolean add(Hotel hotel) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelName", hotel.getName());
        parameters.put("hotelPhone", hotel.getPhone());
        parameters.put("hotelStars", hotel.getStars());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_HOTEL, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Hotel> get(long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_HOTEL_BY_ID, parameters, Mapper.getInstance()));
    }

    /**
     * @param hotel generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Hotel hotel) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelId", hotel.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_HOTEL_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param hotel generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hotelName", hotel.getName());
        parameters.put("hotelPhone", hotel.getPhone());
        parameters.put("hotelStars", hotel.getStars());
        parameters.put("hotelId", hotel.getId());
        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_HOTEL_BY_ID, parameters);
        if (r == 1) {
            return Optional.of(hotel);
        }
        return Optional.empty();
    }
}
