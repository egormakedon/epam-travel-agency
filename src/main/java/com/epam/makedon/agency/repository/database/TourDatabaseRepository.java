package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.entity.impl.TourType;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
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
            tour.setCost(rs.getInt(TOUR_COST));
            tour.setCountry(Country.values()[rs.getInt(COUNTRY_ID) - 1]);
            tour.setDate(rs.getDate(TOUR_DATE).toLocalDate());
            tour.setDescription(rs.getString(TOUR_DESCRIPTION));
            tour.setType(TourType.values()[rs.getInt(TOUR_TYPE_ID) - 1]);
            tour.setPhoto(null);
            tour.setDuration(Duration.ofDays(rs.getDate(TOUR_DURATION).getTime()));

            Hotel hotel = new Hotel();
            hotel.setId(rs.getLong(HOTEL_ID));
            tour.setHotel(hotel);

            return tour;
        }
    }

    private static final String SQL_INSERT_TOUR = "INSERT INTO tour(tour_id,tour_photo,tour_date,tour_duration,fk_country_id,fk_hotel_id,fk_tour_type_id,tour_description,tour_cost) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_TOUR_BY_ID = "SELECT tour_id, tour_photo, tour_date, tour_duration, fk_country_id, fk_hotel_id, fk_tour_type_id, tour_description, tour_cost FROM tour WHERE tour_id=?";
    private static final String SQL_DELETE_TOUR_BY_ID = "DELETE FROM tour WHERE tour_id=?";

    private JdbcTemplate jdbcTemplate;

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
        int r = jdbcTemplate.update(SQL_INSERT_TOUR, tour.getId(), "null", tour.getDate(), tour.getDuration(), tour.getCountry().getId(), tour.getHotel().getId(), tour.getType().getId(), tour.getDescription(), tour.getCost());
        return (r == 1);
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> get(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_TOUR_BY_ID, Mapper.getInstance(), id));
    }

    /**
     * @param tour generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Tour tour) {
        int r = jdbcTemplate.update(SQL_DELETE_TOUR_BY_ID, tour.getId());
        return (r == 1);
    }

    /**
     * @param tour generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        if (remove(tour)) {
            if (add(tour)) {
                return Optional.of(tour);
            } else {
                throw new RepositoryException("tour updated wrong");
            }
        }
        return Optional.empty();
    }
}
