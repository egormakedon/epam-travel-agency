package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.TourService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for {@link TourServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class TourServiceTest {

    @Autowired
    private TourService tourService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        tourService.add(null);
    }

    @Test
    public void exceptionRemoveTest() {
        tourService.remove(null);
    }

    @Test
    public void exceptionUpdateTest() {
        tourService.update(null);
    }

    @Test
    public void exceptionGetTest() {
        tourService.get(-2);
    }
}