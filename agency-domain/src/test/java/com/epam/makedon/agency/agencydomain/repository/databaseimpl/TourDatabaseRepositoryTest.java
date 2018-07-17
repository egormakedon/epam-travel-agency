package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
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

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test for {@link TourDatabaseRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class TourDatabaseRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Test
    public void addTrueTest() {
        Tour tour = Util.getTour();
        assertTrue(tourRepository.add(tour));
    }

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
    public void removeTrueTest() {
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

    @Test
    public void updateTrueTest() {
        Tour tour = Util.getTour();
        tourRepository.add(tour);
        tour.setDescription("hello");
        tourRepository.update(tour);
        assertEquals(tour.getDescription(), tourRepository.get(3).orElseThrow(() -> new RuntimeException("")).getDescription());
    }

    @Test
    public void updateFalseTest() {
        Tour tour = Util.getTour();
        tour.setId(100);
        assertFalse(tourRepository.update(tour).isPresent());
    }

    @Test
    public void findByCriteria() {
        TourRepository service = new TourRepository() {
            @Override
            public boolean add(Tour entity) {
                return false;
            }

            @Override
            public Optional<Tour> get(long id) {
                return Optional.empty();
            }

            @Override
            public boolean remove(Tour entity) {
                return false;
            }

            @Override
            public Optional<Tour> update(Tour entity) {
                return Optional.empty();
            }
        };
        assertNull(service.findByCriteria(null, null, null, null, null, null));
    }

    @Test
    public void findAll() {
        TourRepository service = new TourRepository() {
            @Override
            public boolean add(Tour entity) {
                return false;
            }

            @Override
            public Optional<Tour> get(long id) {
                return Optional.empty();
            }

            @Override
            public boolean remove(Tour entity) {
                return false;
            }

            @Override
            public Optional<Tour> update(Tour entity) {
                return Optional.empty();
            }
        };
        assertNull(service.findAll());
    }
}