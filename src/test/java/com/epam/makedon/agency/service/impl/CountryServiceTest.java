package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.config.TestConfiguration;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountryServiceTest {

    @Autowired
    private CountryService service;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        service.add(null);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        service.remove(null);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        service.update(null);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest1() {
        service.get(0);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest2() {
        service.get(-3);
        fail();
    }

    @Test
    @Transactional
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
    @Transactional
    public void removeTrueTest() {
        assertTrue(service.remove(Country.SPAIN));
    }
}