package com.epam.makedon.agency.config;

import com.epam.makedon.agency.repository.database.*;
import com.epam.makedon.agency.service.*;
import com.epam.makedon.agency.service.impl.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MainConfiguration {

    @Configuration
    @Profile("databaseRepository")
    @Lazy
    @PropertySource(value = "classpath:/property/database.properties")
    public class DatabaseRepositoryConfiguration {

        @Value("${database.password}")
        private String password;

        @Value("${database.schema}")
        private String schema;

        @Value("${database.user}")
        private String user;

        @Value("${database.driverClass}")
        private String driverClass;

        @Value("${database.url}")
        private String url;

        @Value("${database.poolName}")
        private String poolName;

        @Value("${database.dataSourceClassName}")
        private String dataSourceClassName;

        @Value("${database.poolSize}")
        private int poolSize;

        @Value("${database.idleTimeout}")
        private int idleTimeout;

        @Value("${database.characterEncoding}")
        private String characterEncoding;

        @Value("${database.useUnicode}")
        private String useUnicode;

        @Autowired
        private HikariConfig hikariConfig;

        @Autowired
        private DataSource dataSource;

        @Bean()
        public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean(name = "hikariConfig")
        public HikariConfig hikariConfig() {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setPassword(password);
            hikariConfig.setSchema(schema);
            hikariConfig.setUsername(user);
            hikariConfig.setDriverClassName(driverClass);
            hikariConfig.setJdbcUrl(url);
            hikariConfig.setPoolName(poolName);
            hikariConfig.setDataSourceClassName(dataSourceClassName);
            hikariConfig.setMaximumPoolSize(poolSize);
            hikariConfig.setIdleTimeout(idleTimeout);

            Properties properties = new Properties();
            properties.put("characterEncoding", characterEncoding);
            properties.put("useUnicode", useUnicode);
            hikariConfig.setDataSourceProperties(properties);

            return hikariConfig;
        }

        @Bean(name = "dataSource", destroyMethod = "close")
        public HikariDataSource hikariDataSource() {
            return new HikariDataSource(hikariConfig);
        }

        @Bean(name = "jdbcTemplate")
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource);
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

        //-----

        @Bean("countryDatabaseService")
        public CountryService countryService() {
            return new CountryServiceImpl();
        }

        @Bean("hotelDatabaseService")
        public HotelService hotelService() {
            return new HotelServiceImpl();
        }

        @Bean("reviewDatabaseService")
        public ReviewService reviewService() {
            return new ReviewServiceImpl();
        }

        @Bean("tourDatabaseService")
        public TourService tourService() {
            return new TourServiceImpl();
        }

        @Bean("tourTypeDatabaseService")
        public TourTypeService tourTypeService() {
            return new TourTypeServiceImpl();
        }

        @Bean("userDatabaseService")
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
}
