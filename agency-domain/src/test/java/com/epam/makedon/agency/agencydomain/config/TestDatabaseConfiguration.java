package com.epam.makedon.agency.agencydomain.config;

import com.epam.makedon.agency.agencydomain.aspect.RepositoryLogger;
import com.epam.makedon.agency.agencydomain.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.makedon.agency.agencydomain.repository.databaseimpl", "com.epam.makedon.agency.agencydomain.service"})
public class TestDatabaseConfiguration {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("databaseScripts/schema.sql")
                .addScript("databaseScripts/init-data.sql")
                .build();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public RepositoryLogger repositoryLogger() {
        return new RepositoryLogger();
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(Repository.class);
    }
}