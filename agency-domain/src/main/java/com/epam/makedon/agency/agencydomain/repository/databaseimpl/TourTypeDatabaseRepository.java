package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourTypeRepository;
import lombok.Setter;
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
 * Class TourTypeDatabaseRepository implements TourTypeRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 2.0
 */
@Repository
@Profile("databaseRepository")
public class TourTypeDatabaseRepository implements TourTypeRepository {
    private static final String TOUR_TYPE_ID_LITERAL = "tourTypeId";

    private Mapper mapper = new Mapper();

    @Autowired
    @Setter
    private DataSource dataSource;

    /**
     * default constructor
     */
    public TourTypeDatabaseRepository() {}

    /**
     * @param tourType object, which be inserting into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(TourType tourType) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_TOUR_TYPE = "INSERT INTO tour_type (tour_type_id,tour_type_name) VALUES(:tourTypeId,:tourTypeName)";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID_LITERAL, tourType.getId());
        parameters.put("tourTypeName", tourType.toString());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_TOUR_TYPE, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find tourType object in repository
     * @return object, wrapped in optional
     */
    @Override
    public Optional<TourType> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_TOUR_TYPE_NAME_BY_ID = "SELECT tour_type_name name FROM tour_type WHERE tour_type_id=:tourTypeId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID_LITERAL, id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_TOUR_TYPE_NAME_BY_ID, parameters, mapper));
    }

    /**
     * @param tourType object, which be removing from repository
     * @return boolean result of removing
     */
    @Override
    public boolean remove(TourType tourType) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_TOUR_TYPE_BY_ID = "DELETE FROM tour_type WHERE tour_type_id=:tourTypeId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID_LITERAL, tourType.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_TOUR_TYPE_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param tourType object, which be updating in repository
     * @return tourType object, wrapped in optional
     */
    @Override
    public Optional<TourType> update(TourType tourType) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_TOUR_TYPE = "UPDATE tour_type SET tour_type_name=:tourTypeName WHERE tour_type_id=:tourTypeId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(TOUR_TYPE_ID_LITERAL, tourType.getId());
        parameters.put("tourTypeName", tourType.toString());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_TOUR_TYPE, parameters);

        if (r == 1) {
            return Optional.ofNullable(tourType);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<TourType> {
        private static final String NAME = "name";

        @Override
        public TourType mapRow(ResultSet rs, int i) throws SQLException {
            return TourType.valueOf(rs.getString(NAME));
        }
    }
}