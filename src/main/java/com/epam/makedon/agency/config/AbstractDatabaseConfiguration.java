package com.epam.makedon.agency.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:/property/database.properties")
public class AbstractDatabaseConfiguration {

    // dataSource

    @Value("${database.dataSource.url}")
    private String url;

    @Value("${database.dataSource.username}")
    private String username;

    @Value("${database.dataSource.password}")
    private String password;

    @Value("${database.dataSource.driverClass}")
    private String driverClass;

    @Value("${database.dataSource.schema}")
    private String schema;

    // hikari

    @Value("${database.hikari.poolName}")
    private String poolName;

    @Value("${database.hikari.maximumPoolSize}")
    private int maximumPoolSize;

    @Value("${database.hikari.idleTimeout}")
    private int idleTimeout;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClass);
        hikariConfig.setSchema(schema);

        hikariConfig.setPoolName(poolName);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setIdleTimeout(idleTimeout);
        return hikariConfig;
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    public HikariDataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }
}
