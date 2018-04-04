package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        private static final String HOTEL_NAME = "hotelName";
        private static final String COUNTRY_NAME = "countryName";
        private static final String PHONE = "phone";
        private static final String STARS = "stars";

        @Override
        public Hotel mapRow(ResultSet rs, int i) throws SQLException {
            String countryName = rs.getString(COUNTRY_NAME);
            Country country = Country.valueOf(countryName);

            Hotel hotel = new Hotel();
            hotel.setId(rs.getLong(ID));
            hotel.setName(rs.getString(HOTEL_NAME));
            hotel.setPhone(rs.getString(PHONE));
            hotel.setStars(rs.getByte(STARS));
            hotel.setCountry(country);
            return hotel;
        }
    }

    private static final String SQL_SELECT_COUNTRY_ID_BY_NAME = "SELECT country_id id FROM country WHERE country_name=?";
    private static final String SQL_INSERT_HOTEL = "INSERT INTO hotel(hotel_id, hotel_name, hotel_phone, fk_country_id, hotel_stars) VALUES(?,?,?,?,?)";
    private static final String SQL_SELECT_HOTEL_BY_ID = "SELECT hotel.hotel_id id, hotel.hotel_name hotelName, hotel.hotel_phone phone, hotel.hotel_stars stars, country.country_name countryName FROM hotel INNER JOIN country ON hotel.fk_country_id=country.country_id WHERE hotel.hotel_id=?";
    private static final String SQL_DELETE_HOTEL_BY_ID = "DELETE FROM hotel WHERE hotel_id=?";

    private JdbcTemplate jdbcTemplate;

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
        int countryId = jdbcTemplate.queryForObject(SQL_SELECT_COUNTRY_ID_BY_NAME, Integer.class, hotel.getCountry().toString());
        int r = jdbcTemplate.update(SQL_INSERT_HOTEL, hotel.getId(), hotel.getName(), hotel.getPhone(), countryId, hotel.getStars());
        return (r == 1);
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Hotel> get(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_HOTEL_BY_ID, Mapper.getInstance(), id));
    }

    /**
     * @param hotel generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Hotel hotel) {
        int r = jdbcTemplate.update(SQL_DELETE_HOTEL_BY_ID, hotel.getId());
        return (r == 1);
    }

    /**
     * @param hotel generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        if (remove(hotel)) {
            if (add(hotel)) {
                return Optional.of(hotel);
            } else {
                throw new RepositoryException("hotel updated wrong");
            }
        }
        return Optional.empty();
    }
}
