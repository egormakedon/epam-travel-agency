package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.repository.CountryRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Class CountryDatabaseRepository implements CountryRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 2.0
 */
@Repository
@Profile("databaseRepository")
public class CountryDatabaseRepository implements CountryRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDatabaseRepository.class);
    private Mapper mapper = new Mapper();

    @Autowired
    @Setter
    private DataSource dataSource;

    public CountryDatabaseRepository() {}

    /**
     * @param country object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Country country) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_COUNTRY = "INSERT INTO country (country_id,country_name) VALUES(:countryId,:countryName)";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", country.getId());
        parameters.put("countryName", country.toString());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_COUNTRY, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find country in repository
     * @return country object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Country> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_COUNTRY_NAME_BY_ID = "SELECT country_name name FROM country WHERE country_id=:countryId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_COUNTRY_NAME_BY_ID, parameters, mapper));
    }

    /**
     * @param country object to remove from repository
     * @return boolean result of country removing from repository
     */
    @Override
    public boolean remove(Country country) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_COUNTRY_BY_ID = "DELETE FROM country WHERE country_id=:countryId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", country.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_COUNTRY_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param country object to update in repository
     * @return updated country object, wrapped in optional
     */
    @Override
    public Optional<Country> update(Country country) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_COUNTRY = "UPDATE country SET country_name=:countryName WHERE country_id=:countryId";

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("countryId", country.getId());
        parameters.put("countryName", country.toString());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_COUNTRY, parameters);

        if (r == 1) {
            return Optional.ofNullable(country);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Country> {
        private static final String NAME = "name";

        @Override
        public Country mapRow(ResultSet rs, int i) throws SQLException {
            return Country.valueOf(rs.getString(NAME));
        }
    }
}