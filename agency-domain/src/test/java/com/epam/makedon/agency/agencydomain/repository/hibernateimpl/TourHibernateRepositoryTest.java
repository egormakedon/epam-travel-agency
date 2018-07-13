package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

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
    public void addTrueTest() {
        Tour tour = Util.getTour();
        Hotel hotel = Util.getHotel();
        tour.setHotel(hotel);
        assertTrue(tourRepository.add(tour));
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
        Hotel hotel = Util.getHotel();
        tour.setHotel(hotel);
        tourRepository.add(tour);
        tour.setDescription("hello");
        tourRepository.update(tour);
        assertEquals(tour.getDescription(), tourRepository.get(7).orElseThrow(() -> new RuntimeException("")).getDescription());
    }

    @Test
    public void findByCriteria1() {
        List<Tour> tours = tourRepository.findByCriteria(LocalDate.of(2018, Month.JUNE, 10), null, null,null, null, null);
        assertEquals(1, tours.size());
        assertEquals(new BigDecimal(243), tours.get(0).getCost());
    }

    @Test
    public void findByCriteria2() {
        List<Tour> tours = tourRepository.findByCriteria(null, Duration.ofDays(6), Country.SPAIN,null, TourType.WEEKEND, null);
        assertEquals(1, tours.size());
        Tour tour = tours.get(0);
        assertEquals(Duration.ofNanos(6), tour.getDuration());
        assertEquals(Country.SPAIN, tour.getCountry());
        assertEquals(TourType.WEEKEND, tour.getType());
    }

    @Test
    public void findByCriteria3() {
        List<Tour> tours = tourRepository.findByCriteria(null, null, null, Byte.valueOf("5"), null, null);
        assertEquals(1, tours.get(0).getHotel().getId());
        assertEquals("1234567", tours.get(0).getHotel().getPhone());
        assertEquals("hotel1", tours.get(0).getHotel().getName());
        assertEquals(tours.get(0).getHotel(), tours.get(1).getHotel());
        assertEquals(tours.get(1).getHotel(), tours.get(2).getHotel());
        assertEquals(tours.get(2).getHotel(), tours.get(3).getHotel());
    }

    @Test
    public void findAll() {
        List<Tour> tours = tourRepository.findAll();
        assertEquals(5, tours.size());
    }
}