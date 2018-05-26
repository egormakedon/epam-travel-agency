package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourTypeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.Optional;

import static org.junit.Assert.*;

public class TourTypeDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static TourTypeRepository repository;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        repository = context.getBean("tourTypeDatabaseRepository", TourTypeRepository.class);
    }

    @After
    public void destroy() {
        ((EmbeddedDatabase)context.getBean("dataSource")).shutdown();
        context = null;
        repository = null;
    }

    @Test
    public void addTrueTest() {
        TourType c = TourType.EXCURSION;
        assertTrue(repository.add(c));
    }

    @Test(expected = DuplicateKeyException.class)
    public void addExcTest() {
        TourType c = TourType.CHILDREN;
        repository.add(c);
        fail();
    }

    @Test
    public void getTrueTest() {
        TourType c = TourType.CHILDREN;
        Optional<TourType> opt = repository.get(1);
        assertEquals(c, opt.get());
    }

    @Test
    public void getFalseTest() {
        TourType c = TourType.CHILDREN;
        Optional<TourType> opt = repository.get(4);
        assertNotEquals(c, opt.get());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        Optional<TourType> opt = repository.get(100);
        fail();
    }

    @Test
    public void removeTrueTest() {
        TourType c = TourType.CHILDREN;
        assertTrue(repository.remove(c));
    }

    @Test
    public void removeExcTest() {
        TourType c = TourType.EXCURSION;
        assertFalse(repository.remove(c));
    }
}
