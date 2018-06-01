package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.repository.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountryDatabaseRepositoryTest {

    @Autowired
    private CountryRepository repository;

    @Test
    public void addTrueTest() {
        Country c = Country.CHINA;
        assertTrue(repository.add(c));
    }

    @Test(expected = DuplicateKeyException.class)
    public void addExcTest() {
        Country s = Country.BELARUS;
        repository.add(s);
    }

    @Test
    public void getTrueTest() {
        Country s = Country.SPAIN;
        Optional<Country> opt = repository.get(4);
        assertEquals(s, opt.orElse(null));
    }

    @Test
    public void getFalseTest() {
        Country s = Country.SPAIN;
        Optional<Country> opt = repository.get(5);
        assertNotEquals(s, opt.orElse(null));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        repository.get(100);
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