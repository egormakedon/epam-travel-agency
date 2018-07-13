package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.TourService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

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

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        tourService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        tourService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        tourService.get(-2);
    }

    @Test
    public void addTrueTest() {
        Tour tour = Util.getTour();
        assertTrue(tourService.add(tour));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(tourService.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(tourService.get(2).orElse(null));
    }

    @Test
    public void getFalseTest() {
        Tour tour = Util.getTour();
        assertNotEquals(tourService.get(1).orElse(null), tour);
    }

    @Test
    public void removeTrueTest() {
        Tour tour = new Tour();
        tour.setId(1);
        assertTrue(tourService.remove(tour));
    }

    @Test
    public void removeFalseTest() {
        Tour tour = new Tour();
        tour.setId(-1);
        assertFalse(tourService.remove(tour));
    }

    @Test
    public void updateTrueTest() {
        Tour tour = Util.getTour();
        tourService.add(tour);
        tour.setDescription("hello");
        tourService.update(tour);
        assertEquals(tour.getDescription(), tourService.get(3).orElseThrow(() -> new RuntimeException("")).getDescription());
    }

    @Test
    public void updateFalseTest() {
        Tour tour = Util.getTour();
        tour.setId(100);
        assertFalse(tourService.update(tour).isPresent());
    }
}