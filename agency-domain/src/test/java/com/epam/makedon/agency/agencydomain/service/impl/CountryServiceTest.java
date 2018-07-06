package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.service.CountryService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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
}