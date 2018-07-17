package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.repository.CountryRepository;
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
 * Repository class for {@link Country} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainDatabaseConfiguration} class,
 * implements {@link CountryRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("databaseRepository")

public class CountryDatabaseRepository implements CountryRepository {

    private static final String COUNTRY_ID = "countryId";
    private static final String COUNTRY_NAME = "countryName";

    @Autowired
    private DataSource dataSource;
    private Mapper mapper = new Mapper();

    /**
     * default constructor
     */
    public CountryDatabaseRepository() {}

    /**
     * Add operation
     *
     * @param country {@link Country}
     * @return true/false
     */
    @Override
    public boolean add(Country country) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_COUNTRY = "INSERT " +
                "INTO country (country_id,country_name) " +
                "VALUES(:countryId,:countryName)";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(COUNTRY_ID, country.getId());
        parameters.put(COUNTRY_NAME, country.toString());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_COUNTRY, parameters);
        return r == 1;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Country> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_COUNTRY_NAME_BY_ID = "SELECT country_name countryName " +
                "FROM country " +
                "WHERE country_id=:countryId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(COUNTRY_ID, id);
        return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_COUNTRY_NAME_BY_ID, parameters, mapper));
    }

    /**
     * Remove operation
     *
     * @param country {@link Country}
     * @return true/false
     */
    @Override
    public boolean remove(Country country) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_COUNTRY_BY_ID = "DELETE " +
                "FROM country " +
                "WHERE country_id=:countryId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(COUNTRY_ID, country.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_COUNTRY_BY_ID, parameters);
        return r == 1;
    }

    /**
     * Update operation
     *
     * @param country {@link Country}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Country> update(Country country) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_COUNTRY = "UPDATE country " +
                "SET country_name=:countryName " +
                "WHERE country_id=:countryId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put(COUNTRY_ID, country.getId());
        parameters.put(COUNTRY_NAME, country.toString());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_COUNTRY, parameters);

        if (r == 1) {
            return Optional.of(country);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Country> {

        @Override
        public Country mapRow(ResultSet rs, int i) throws SQLException {
            return Country.valueOf(rs.getString(COUNTRY_NAME));
        }
    }
}