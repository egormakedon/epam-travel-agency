package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Test for {@link TourTypeDatabaseRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class TourTypeDatabaseRepositoryTest {

    @Autowired
    private TourTypeRepository tourTypeRepository;

    @Test
    public void addTrueTest() {
        TourType tourType = TourType.EXCURSION;
        assertTrue(tourTypeRepository.add(tourType));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(tourTypeRepository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(tourTypeRepository.get(3).orElse(null));
    }

    @Test
    public void getFalseTest() {
        TourType tourType = TourType.CHILDREN;
        assertNotEquals(tourType, tourTypeRepository.get(4).orElse(null));
    }

    @Test
    public void removeTrueTest() {
        TourType tourType = TourType.CHILDREN;
        assertTrue(tourTypeRepository.remove(tourType));
    }

    @Test
    public void removeFalseTest() {
        TourType tourType = TourType.EXCURSION;
        assertFalse(tourTypeRepository.remove(tourType));
    }

    @Test
    public void updateTest1() {
        TourType tourType = TourType.EXCURSION;
        assertNull(tourTypeRepository.update(tourType).orElse(null));
    }

    @Test
    public void updateTest2() {
        TourType tourType = TourType.WEEKEND;
        assertEquals(tourTypeRepository.update(tourType).orElse(null), tourType);
    }
}