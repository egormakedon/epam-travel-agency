package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.service.HotelService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for {@link HotelServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        hotelService.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        hotelService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        hotelService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        hotelService.get(-10);
    }
}