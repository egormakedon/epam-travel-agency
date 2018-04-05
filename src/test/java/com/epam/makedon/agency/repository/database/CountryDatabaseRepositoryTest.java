package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Country;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class CountryDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static CountryDatabaseRepository repository;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void init() {
//        context = new ClassPathXmlApplicationContext("test.xml");
//        repository = context.getBean("countryDatabaseRepository", CountryDatabaseRepository.class);

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("databaseScripts/h2/schema.sql")
                .addScript("databaseScripts/h2/init-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
    }

    @After
    public void destroy() {
        context = null;
        repository = null;
    }

    @Test
    public void addTest() {
        context = new ClassPathXmlApplicationContext("test.xml");
        repository = context.getBean("countryDatabaseRepository", CountryDatabaseRepository.class);
        repository.setJdbcTemplate(jdbcTemplate);

        Country china = Country.CHINA;
        assertTrue(repository.add(china));
    }
}
