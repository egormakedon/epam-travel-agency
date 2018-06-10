package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.service.CountryService;
import com.epam.makedon.agency.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountryServiceTest {

    @Autowired
    private CountryService service;

    @Test
    public void exceptionAddTest() {
        try {
            service.add(null);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionRemoveTest() {
        try {
            service.remove(null);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionUpdateTest() {
        try {
            service.update(null);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionGetTest1() {
        try {
            service.get(0);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionGetTest2() {
        try {
            service.get(-3);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void addTrueTest() {
        assertTrue(service.add(Country.CHINA));
    }

    @Test
    public void getTrueTest() {
        Country c = Country.SPAIN;
        Optional<Country> opt = service.get(4);
        assertEquals(c, opt.orElse(null));
    }

    @Test
    public void removeTrueTest() {
        assertTrue(service.remove(Country.SPAIN));
    }

    @Test
    public void updateTrueTest1() {
        assertNotNull(service.update(Country.SPAIN).orElse(null));
    }

    @Test
    public void updateTrueTest2() {
        assertNotNull(service.update(Country.ENGLAND).orElse(null));
    }

    @Test
    public void updateTrueTest3() {
        assertNotNull(service.update(Country.BELARUS).orElse(null));
    }
}