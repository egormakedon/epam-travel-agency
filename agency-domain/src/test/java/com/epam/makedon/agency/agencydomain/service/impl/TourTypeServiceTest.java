package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.TourTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for {@link TourTypeServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class TourTypeServiceTest {

    @Autowired
    private TourTypeService tourTypeService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        tourTypeService.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        tourTypeService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        tourTypeService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        tourTypeService.get(0);
    }

    @Test
    public void addTrueTest() {
        TourType tourType = TourType.EXCURSION;
        assertTrue(tourTypeService.add(tourType));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(tourTypeService.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(tourTypeService.get(3).orElse(null));
    }

    @Test
    public void getFalseTest() {
        TourType tourType = TourType.CHILDREN;
        assertNotEquals(tourType, tourTypeService.get(4).orElse(null));
    }

    @Test
    public void removeTrueTest() {
        TourType tourType = TourType.CHILDREN;
        assertTrue(tourTypeService.remove(tourType));
    }

    @Test
    public void removeFalseTest() {
        TourType tourType = TourType.EXCURSION;
        assertFalse(tourTypeService.remove(tourType));
    }

    @Test
    public void updateTest1() {
        TourType tourType = TourType.EXCURSION;
        assertNull(tourTypeService.update(tourType).orElse(null));
    }

    @Test
    public void updateTest2() {
        TourType tourType = TourType.WEEKEND;
        assertEquals(tourTypeService.update(tourType).orElse(null), tourType);
    }
}