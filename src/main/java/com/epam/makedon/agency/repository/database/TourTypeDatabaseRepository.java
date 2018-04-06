package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.TourType;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Class {@code TourTypeDatabaseRepository} implements {@code TourTypeRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
public class TourTypeDatabaseRepository implements com.epam.makedon.agency.repository.TourTypeRepository {
    private static final Logger LOGGER;
    private static TourTypeDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(TourTypeDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private static class Mapper implements RowMapper<TourType> {
        private static final Mapper INSTANCE = new Mapper();
        private Mapper() {}
        public static Mapper getInstance() { return INSTANCE; }

        private static final String NAME = "name";

        @Override
        public TourType mapRow(ResultSet rs, int i) throws SQLException {
            return TourType.valueOf(rs.getString(NAME));
        }
    }

    private static final String SQL_INSERT_TOUR_TYPE = "INSERT INTO tour_type (tour_type_id,tour_type_name) VALUES(:tourTypeId,:tourTypeName)";
    private static final String SQL_SELECT_TOUR_TYPE_NAME_BY_ID = "SELECT tour_type_name name FROM tour_type WHERE tour_type_id=:tourTypeId";
    private static final String SQL_DELETE_TOUR_TYPE_BY_ID = "DELETE FROM tour_type WHERE tour_type_id=:tourTypeId";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private TourTypeDatabaseRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }
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

    public static TourTypeDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new TourTypeDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param tourType object, which be insert into repository
     * @return boolean result
     */
    @Override
    public boolean add(TourType tourType) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("tourTypeId", tourType.getId());
        parameters.put("tourTypeName", tourType.toString());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_TOUR_TYPE, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<TourType> get(long id) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("tourTypeId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_TOUR_TYPE_NAME_BY_ID, parameters, Mapper.getInstance()));
    }

    /**
     * @param tourType generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(TourType tourType) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("tourTypeId", tourType.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_TOUR_TYPE_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param tourType generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<TourType> update(TourType tourType) {
        if (remove(tourType)) {
            if (add(tourType)) {
                return Optional.of(tourType);
            } else {
                throw new RepositoryException("tourType updated wrong");
            }
        }
        return Optional.empty();
    }
}
