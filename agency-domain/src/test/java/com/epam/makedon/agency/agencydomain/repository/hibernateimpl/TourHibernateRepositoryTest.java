package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.repository.TourRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Test for {@link TourHibernateRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional

public class TourHibernateRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Test
    public void getTrueTest1() {
        assertNotNull(tourRepository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(tourRepository.get(2).orElse(null));
    }

    @Test
    public void getFalseTest() {
        Tour tour = Util.getTour();
        assertNotEquals(tourRepository.get(1).orElse(null), tour);
    }

    @Test
    public void removeTrueTest1() {
        Tour tour = new Tour();
        tour.setId(1);
        assertTrue(tourRepository.remove(tour));
    }

    @Test
    public void removeFalseTest() {
        Tour tour = new Tour();
        tour.setId(-1);
        assertFalse(tourRepository.remove(tour));
    }
}