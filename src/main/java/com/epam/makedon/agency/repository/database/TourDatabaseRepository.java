package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.entity.impl.TourType;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code TourDatabaseRepository} implements {@code TourRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
public class TourDatabaseRepository implements com.epam.makedon.agency.repository.TourRepository {
    private static final Logger LOGGER;
    private static TourDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(TourDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private static class Mapper implements RowMapper<Tour> {
        private static final Mapper INSTANCE = new Mapper();
        private Mapper() {}
        public static Mapper getInstance() { return INSTANCE; }

        private static final String TOUR_ID = "tour_id";
        private static final String TOUR_PHOTO = "tour_photo";
        private static final String TOUR_DATE = "tour_date";
        private static final String TOUR_DURATION = "tour_duration";
        private static final String COUNTRY_ID = "fk_country_id";
        private static final String HOTEL_ID = "fk_hotel_id";
        private static final String TOUR_TYPE_ID = "fk_tour_type_id";
        private static final String TOUR_DESCRIPTION = "tour_description";
        private static final String TOUR_COST = "tour_cost";

        @Override
        public Tour mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(TOUR_ID));
            tour.setCost(rs.getBigDecimal(TOUR_COST));

            long countryId = rs.getLong(COUNTRY_ID);
            Country[] countries = Country.values();
            Country country = null;
            for (Country c : countries) {
                if (c.getId() == countryId) {
                    country = c;
                    break;
                }
            }
            tour.setCountry(country);

            long tourTypeId = rs.getLong(TOUR_TYPE_ID);
            TourType[] tourTypes = TourType.values();
            TourType tourType = null;
            for (TourType t : tourTypes) {
                if (t.getId() == tourTypeId) {
                    tourType = t;
                    break;
                }
            }
            tour.setType(tourType);

            tour.setDate(rs.getDate(TOUR_DATE).toLocalDate());
            tour.setDescription(rs.getString(TOUR_DESCRIPTION));
            tour.setDuration(Duration.ofDays(rs.getLong(TOUR_DURATION)));

            Hotel hotel = new Hotel();
            hotel.setId(rs.getLong(HOTEL_ID));
            tour.setHotel(hotel);

            tour.setPhoto(null); //

            return tour;
        }
    }

    private static final String SQL_INSERT_TOUR = "INSERT INTO tour (tour_photo,tour_date,tour_duration,fk_country_id,fk_hotel_id,fk_tour_type_id,tour_description,tour_cost) " +
            "VALUES(:tourPhoto, :tourDate, :tourDuration, :countryId, :hotelId, :tourType, :tourDescription, :tourCost)";
    private static final String SQL_SELECT_TOUR_BY_ID = "SELECT tour_id, tour_photo, tour_date, tour_duration, fk_country_id, fk_hotel_id, fk_tour_type_id, tour_description, tour_cost FROM tour WHERE tour_id=:tourId";
    private static final String SQL_DELETE_TOUR_BY_ID = "DELETE FROM tour WHERE tour_id=:tourId";
    private static final String SQL_UPDATE_TOUR_BY_ID = "UPDATE tour SET tour_photo=:tourPhoto, tour_date=:tourDate, tour_duration=:tourDuration, fk_country_id=:countryId," +
            "fk_hotel_id=:hotelId, fk_tour_type_id=:tourType, tour_description=:tourDescription, tour_cost=:tourCost";

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private TourDatabaseRepository() {
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

    public static TourDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new TourDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param tour object, which be insert into repository
     * @return boolean result
     */
    @Override
    public boolean add(Tour tour) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourPhoto", "null");
        parameters.put("tourDate", Date.valueOf(tour.getDate()));
        parameters.put("tourDuration", tour.getDuration().toDays());
        parameters.put("countryId", tour.getCountry().getId());
        parameters.put("hotelId", tour.getHotel().getId());
        parameters.put("tourType", tour.getType().getId());
        parameters.put("tourDescription", tour.getDescription());
        parameters.put("tourCost", tour.getCost());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_TOUR, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> get(long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_TOUR_BY_ID, parameters, Mapper.getInstance()));
    }

    /**
     * @param tour generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Tour tour) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", tour.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_TOUR_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param tour generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourPhoto", "null");
        parameters.put("tourDate", Date.valueOf(tour.getDate()));
        parameters.put("tourDuration", tour.getDuration().toDays());
        parameters.put("countryId", tour.getCountry().getId());
        parameters.put("hotelId", tour.getHotel().getId());
        parameters.put("tourType", tour.getType().getId());
        parameters.put("tourDescription", tour.getDescription());
        parameters.put("tourCost", tour.getCost());
        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_TOUR_BY_ID, parameters);
        if (r == 1) {
            return Optional.of(tour);
        }
        return Optional.empty();
    }
}
