package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.repository.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test for {@link CountryDatabaseRepository} class
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class CountryDatabaseRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void addTrueTest() {
        Country country = Country.CHINA;
        assertTrue(countryRepository.add(country));
    }

    @Test
    public void getTrueTest1() {
        Country country = Country.SPAIN;
        Optional<Country> opt = countryRepository.get(4);
        assertEquals(country, opt.orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(countryRepository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest3() {
        Country country = Country.ENGLAND;
        Optional<Country> opt = countryRepository.get(5);
        assertEquals(country, opt.orElse(null));
    }

    @Test
    public void getFalseTest() {
        Country country = Country.SPAIN;
        Optional<Country> opt = countryRepository.get(5);
        assertNotEquals(country, opt.orElse(null));
    }

    @Test
    public void removeTrueTest1() {
        Country country = Country.SPAIN;
        assertTrue(countryRepository.remove(country));
    }

    @Test
    public void removeTrueTest2() {
        Country country = Country.BELARUS;
        assertTrue(countryRepository.remove(country));
    }

    @Test
    public void removeTrueTest3() {
        Country country = Country.ENGLAND;
        assertTrue(countryRepository.remove(country));
    }

    @Test
    public void removeFalseTest() {
        Country country = Country.CHINA;
        assertFalse(countryRepository.remove(country));
    }

    @Test
    public void updateTest1() {
        Country country = Country.CHINA;
        assertNull(countryRepository.update(country).orElse(null));
    }

    @Test
    public void updateTest2() {
        Country country = Country.ENGLAND;
        assertEquals(countryRepository.update(country).orElse(null), country);
    }
}