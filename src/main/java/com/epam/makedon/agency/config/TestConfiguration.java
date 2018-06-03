package com.epam.makedon.agency.config;

import com.epam.makedon.agency.aspect.RepositoryLogger;
import com.epam.makedon.agency.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.databaseimpl")
@ComponentScan("com.epam.makedon.agency.service")
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

    @Bean("repositoryLogger")
    public RepositoryLogger repositoryLogger() {
        return new RepositoryLogger();
    }

    @Bean("logger")
    public Logger logger() {
        return LoggerFactory.getLogger(Repository.class);
    }
}