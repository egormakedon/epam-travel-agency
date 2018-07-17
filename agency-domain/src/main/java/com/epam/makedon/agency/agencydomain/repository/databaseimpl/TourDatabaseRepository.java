package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class for {@link Tour} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainDatabaseConfiguration} class,
 * implements {@link TourRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("databaseRepository")

public class TourDatabaseRepository implements TourRepository {

    private static final String TOUR_PHOTO = "tourPhoto";
    private static final String TOUR_ID = "tourId";
    private static final String TOUR_DATE = "tourDate";
    private static final String TOUR_DURATION = "tourDuration";
    private static final String TOUR_TYPE_ID = "tourTypeId";
    private static final String TOUR_DESCRIPTION = "tourDescription";
    private static final String TOUR_COST = "tourCost";

    private static final String COUNTRY_ID = "countryId";
    private static final String HOTEL_ID = "hotelId";

    @Autowired
    private DataSource dataSource;
    private Mapper mapper = new Mapper();

    /**
     * default constructor
     */
    public TourDatabaseRepository() {}

    /**
     * Add operation
     *
     * @param tour {@link Tour}
     * @return true/false
     */
    @Override
    public boolean add(Tour tour) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_TOUR = "INSERT INTO tour (tour_photo, tour_date, " +
                "tour_duration, fk_country_id, fk_hotel_id, fk_tour_type_id, tour_description, tour_cost) " +
                "VALUES(:tourPhoto, :tourDate, :tourDuration, :countryId, :hotelId, :tourTypeId, " +
                ":tourDescription, :tourCost)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TOUR_PHOTO, tour.getPhoto());
        parameters.put(TOUR_DATE, Date.valueOf(tour.getDate()));
        parameters.put(TOUR_DURATION, tour.getDuration().toDays());
        parameters.put(TOUR_TYPE_ID, tour.getType().getId());
        parameters.put(TOUR_DESCRIPTION, tour.getDescription());
        parameters.put(TOUR_COST, tour.getCost());

        parameters.put(COUNTRY_ID, tour.getCountry().getId());
        parameters.put(HOTEL_ID, tour.getHotel().getId());

        int r = namedParameterJdbcTemplate.update(SQL_INSERT_TOUR, parameters);
        return r == 1;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Tour> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_TOUR_BY_ID = "SELECT tour_id tourId, tour_photo tourPhoto, " +
                "tour_date tourDate, tour_duration tourDuration, fk_country_id countryId, " +
                "fk_hotel_id hotelId, fk_tour_type_id tourTypeId, tour_description tourDescription, " +
                "tour_cost tourCost " +
                "FROM tour " +
                "WHERE tour_id=:tourId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TOUR_ID, id);
        return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_TOUR_BY_ID, parameters,mapper));
    }

    /**
     * Remove operation
     *
     * @param tour {@link Tour}
     * @return true/false
     */
    @Override
    public boolean remove(Tour tour) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_TOUR_BY_ID = "DELETE " +
                "FROM tour " +
                "WHERE tour_id=:tourId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TOUR_ID, tour.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_TOUR_BY_ID, parameters);
        return r == 1;
    }

    /**
     * Update operation
     *
     * @param tour {@link Tour}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_TOUR_BY_ID = "UPDATE tour " +
                "SET tour_photo=:tourPhoto, tour_date=:tourDate, tour_duration=:tourDuration, " +
                "fk_country_id=:countryId, fk_hotel_id=:hotelId, fk_tour_type_id=:tourTypeId, " +
                "tour_description=:tourDescription, tour_cost=:tourCost";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TOUR_PHOTO, tour.getPhoto());
        parameters.put(TOUR_DATE, Date.valueOf(tour.getDate()));
        parameters.put(TOUR_DURATION, tour.getDuration().toDays());
        parameters.put(COUNTRY_ID, tour.getCountry().getId());
        parameters.put(HOTEL_ID, tour.getHotel().getId());
        parameters.put(TOUR_TYPE_ID, tour.getType().getId());
        parameters.put(TOUR_DESCRIPTION, tour.getDescription());
        parameters.put(TOUR_COST, tour.getCost());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_TOUR_BY_ID, parameters);

        if (r == 1) {
            return Optional.of(tour);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Tour> {

        @Override
        public Tour mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(TOUR_ID));
            tour.setCost(rs.getBigDecimal(TOUR_COST));
            tour.setPhoto(rs.getString(TOUR_PHOTO));
            tour.setDate(rs.getDate(TOUR_DATE).toLocalDate());
            tour.setDescription(rs.getString(TOUR_DESCRIPTION));
            tour.setDuration(Duration.ofDays(rs.getLong(TOUR_DURATION)));
            tour.setCountry(Country.fromCode(rs.getLong(COUNTRY_ID)));
            tour.setType(TourType.fromCode(rs.getLong(TOUR_TYPE_ID)));

            Hotel hotel = new Hotel();
            hotel.setId(rs.getLong(HOTEL_ID));
            tour.setHotel(hotel);

            return tour;
        }
    }
}