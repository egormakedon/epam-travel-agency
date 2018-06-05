package com.epam.makedon.agency.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.databaseimpl")
@ComponentScan("com.epam.makedon.agency.service")
@Lazy
@PropertySource(value = "classpath:/property/database.properties")
public class MainConfiguration {

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

    @Bean(name = "propertySourcesPlaceholderConfigurer")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
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

    @Bean(name = "txManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}