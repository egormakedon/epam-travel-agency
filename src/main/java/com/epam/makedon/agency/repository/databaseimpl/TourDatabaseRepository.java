package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Class TourDatabaseRepository implements TourRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 2.0
 */
@Repository
@Profile("databaseRepository")
public class TourDatabaseRepository implements TourRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourDatabaseRepository.class);
    private Mapper mapper = new Mapper();

    @Autowired
    @Setter
    private DataSource dataSource;

    public TourDatabaseRepository() {}

    /**
     * @param tour object, which be inserting into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Tour tour) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_TOUR = "INSERT INTO tour (tour_photo,tour_date,tour_duration,fk_country_id,fk_hotel_id,fk_tour_type_id,tour_description,tour_cost) " +
                "VALUES(:tourPhoto, :tourDate, :tourDuration, :countryId, :hotelId, :tourType, :tourDescription, :tourCost)";

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
     * @param id to define and find tour in repository
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_TOUR_BY_ID = "SELECT tour_id, tour_photo, tour_date, tour_duration, fk_country_id, fk_hotel_id, fk_tour_type_id, tour_description, tour_cost " +
                "FROM tour WHERE tour_id=:tourId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_TOUR_BY_ID, parameters,mapper));
    }

    /**
     * @param tour object, which be removing from repository
     * @return boolean result of removing tour object
     */
    @Override
    public boolean remove(Tour tour) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_TOUR_BY_ID = "DELETE FROM tour WHERE tour_id=:tourId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", tour.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_TOUR_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param tour object. which be updating in repository
     * @return tour object, wrapped in optional
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_TOUR_BY_ID = "UPDATE tour SET tour_photo=:tourPhoto, tour_date=:tourDate, tour_duration=:tourDuration, fk_country_id=:countryId," +
                "fk_hotel_id=:hotelId, fk_tour_type_id=:tourType, tour_description=:tourDescription, tour_cost=:tourCost";

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
            return Optional.ofNullable(tour);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Tour> {
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

            tour.setPhoto(null); ///////////////////////////////////////////////////////////////////////////////////////

            return tour;
        }
    }
}