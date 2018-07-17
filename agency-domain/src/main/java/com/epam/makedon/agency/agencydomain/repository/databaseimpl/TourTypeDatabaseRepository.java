package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourTypeRepository;
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
 * Repository class for {@link TourType} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainDatabaseConfiguration} class,
 * implements {@link TourTypeRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("databaseRepository")

public class TourTypeDatabaseRepository implements TourTypeRepository {

    private static final String TOUR_TYPE_ID = "tourTypeId";
    private static final String TOUR_TYPE_NAME = "tourTypeName";

    @Autowired
    private DataSource dataSource;
    private Mapper mapper = new Mapper();

    /**
     * default constructor
     */
    public TourTypeDatabaseRepository() {}

    /**
     * Add operation
     *
     * @param tourType {@link TourType}
     * @return true/false
     */
    @Override
    public boolean add(TourType tourType) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_TOUR_TYPE = "INSERT INTO tour_type (tour_type_id,tour_type_name) " +
                "VALUES(:tourTypeId,:tourTypeName)";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID, tourType.getId());
        parameters.put(TOUR_TYPE_NAME, tourType.toString());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_TOUR_TYPE, parameters);
        return r == 1;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<TourType> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_TOUR_TYPE_NAME_BY_ID = "SELECT tour_type_name tourTypeName " +
                "FROM tour_type " +
                "WHERE tour_type_id=:tourTypeId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID, id);
        return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_TOUR_TYPE_NAME_BY_ID, parameters, mapper));
    }

    /**
     * Remove operation
     *
     * @param tourType {@link TourType}
     * @return true/false
     */
    @Override
    public boolean remove(TourType tourType) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_TOUR_TYPE_BY_ID = "DELETE " +
                "FROM tour_type " +
                "WHERE tour_type_id=:tourTypeId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID, tourType.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_TOUR_TYPE_BY_ID, parameters);
        return r == 1;
    }

    /**
     * Update operation
     *
     * @param tourType {@link TourType}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<TourType> update(TourType tourType) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_TOUR_TYPE = "UPDATE tour_type " +
                "SET tour_type_name=:tourTypeName " +
                "WHERE tour_type_id=:tourTypeId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID, tourType.getId());
        parameters.put(TOUR_TYPE_NAME, tourType.toString());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_TOUR_TYPE, parameters);

        if (r == 1) {
            return Optional.of(tourType);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<TourType> {

        @Override
        public TourType mapRow(ResultSet rs, int i) throws SQLException {
            return TourType.valueOf(rs.getString(TOUR_TYPE_NAME));
        }
    }
}