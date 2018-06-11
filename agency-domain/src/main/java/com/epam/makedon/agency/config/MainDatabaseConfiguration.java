package com.epam.makedon.agency.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.makedon.agency.repository.databaseimpl", "com.epam.makedon.agency.service.impl"})
@Import(AbstractDatabaseConfiguration.class)
public class MainDatabaseConfiguration {

    @Autowired
    private HikariDataSource hikariDataSource;

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(hikariDataSource);
    }
}