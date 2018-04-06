package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Country;
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
 * Class {@code CountryDatabaseRepository} implements {@code CountryRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
public class CountryDatabaseRepository implements com.epam.makedon.agency.repository.CountryRepository {
    private static final Logger LOGGER;
    private static CountryDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(CountryDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private static class Mapper implements RowMapper<Country> {
        private static final Mapper INSTANCE = new Mapper();
        private Mapper() {}
        public static Mapper getInstance() { return INSTANCE; }

        private static final String NAME = "name";

        @Override
        public Country mapRow(ResultSet rs, int i) throws SQLException {
            return Country.valueOf(rs.getString(NAME));
        }
    }

    private static final String SQL_INSERT_COUNTRY = "INSERT INTO country (country_id,country_name) VALUES(:countryId,:countryName)";
    private static final String SQL_SELECT_COUNTRY_NAME_BY_ID = "SELECT country_name name FROM country WHERE country_id=:countryId";
    private static final String SQL_DELETE_COUNTRY_BY_ID = "DELETE FROM country WHERE country_id=:countryId";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private CountryDatabaseRepository() {
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

    public static CountryDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new CountryDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param country object, which be insert into repository
     * @return boolean result
     */
    @Override
    public boolean add(Country country) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", country.getId());
        parameters.put("countryName", country.toString());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_COUNTRY, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Country> get(long id) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_COUNTRY_NAME_BY_ID, parameters, Mapper.getInstance()));
    }

    /**
     * @param country generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Country country) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", country.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_COUNTRY_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param country generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Country> update(Country country) {
        if (remove(country)) {
            if (add(country)) {
                return Optional.of(country);
            } else {
                throw new RepositoryException("country updated wrong");
            }
        }
        return Optional.empty();
    }
}