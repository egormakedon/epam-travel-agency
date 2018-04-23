package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.entity.impl.Country;
import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.Optional;

public class CountryDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static CountryDatabaseRepository repository;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        repository = context.getBean("countryDatabaseRepository", CountryDatabaseRepository.class);
    }

    @After
    public void destroy() {
        ((EmbeddedDatabase)context.getBean("dataSource")).shutdown();
        context = null;
        repository = null;
    }

    @Test
    public void addTrueTest() {
        Country c = Country.CHINA;
        assertTrue(repository.add(c));
    }

    @Test(expected = DuplicateKeyException.class)
    public void addExcTest() {
        Country s = Country.SPAIN;
        repository.add(s);
        fail();
    }

    @Test
    public void getTrueTest() {
        Country s = Country.SPAIN;
        Optional<Country> opt = repository.get(4);
        assertEquals(s, opt.get());
    }

    @Test
    public void getFalseTest() {
        Country s = Country.SPAIN;
        Optional<Country> opt = repository.get(5);
        assertNotEquals(s, opt.get());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        Optional<Country> opt = repository.get(100);
        fail();
    }

    @Test
    public void removeTrueTest() {
        Country s = Country.SPAIN;
        assertTrue(repository.remove(s));
    }

    @Test
    public void removeExcTest() {
        Country c = Country.CHINA;
        assertFalse(repository.remove(c));
    }
}
