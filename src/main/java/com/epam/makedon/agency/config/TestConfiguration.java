package com.epam.makedon.agency.config;

import com.epam.makedon.agency.repository.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TestConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase embeddedDatabase = builder.setType(EmbeddedDatabaseType.H2)
                .addScript("databaseScripts/schema.sql")
                .addScript("databaseScripts/init-data.sql")
                .build();
        return embeddedDatabase;
    }

    @Bean(name = "txManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "countryDatabaseRepository")
    public CountryDatabaseRepository countryDatabaseRepository() {
        return CountryDatabaseRepository.getInstance();
    }

    @Bean(name = "hotelDatabaseRepository")
    public HotelDatabaseRepository hotelDatabaseRepository() {
        return HotelDatabaseRepository.getInstance();
    }

    @Bean(name = "reviewDatabaseRepository")
    public ReviewDatabaseRepository reviewDatabaseRepository() {
        return ReviewDatabaseRepository.getInstance();
    }

    @Bean(name = "tourDatabaseRepository")
    public TourDatabaseRepository tourDatabaseRepository() {
        return TourDatabaseRepository.getInstance();
    }

    @Bean(name = "tourTypeDatabaseRepository")
    public TourTypeDatabaseRepository tourTypeDatabaseRepository() {
        return TourTypeDatabaseRepository.getInstance();
    }

    @Bean(name = "userDatabaseRepository")
    public UserDatabaseRepository userDatabaseRepository() {
        return UserDatabaseRepository.getInstance();
    }
}