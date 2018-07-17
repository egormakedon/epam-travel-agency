package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.service.CountryService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link CountryServiceImpl} class
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        countryService.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        countryService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        countryService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        countryService.get(-3);
    }

    @Test
    public void addTrueTest() {
        assertTrue(countryService.add(Country.CHINA));
    }

    @Test
    public void getTrueTest() {
        Country c = Country.SPAIN;
        Optional<Country> opt = countryService.get(4);
        assertEquals(c, opt.orElse(null));
    }

    @Test
    public void removeTrueTest() {
        assertTrue(countryService.remove(Country.SPAIN));
    }

    @Test
    public void updateTrueTest1() {
        assertNotNull(countryService.update(Country.SPAIN).orElse(null));
    }

    @Test
    public void updateTrueTest2() {
        assertNotNull(countryService.update(Country.ENGLAND).orElse(null));
    }

    @Test
    public void updateTrueTest3() {
        assertNotNull(countryService.update(Country.BELARUS).orElse(null));
    }
}