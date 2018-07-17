package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.service.TourService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link TourServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"hibernateRepository",
        "service"})
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class TourServiceHibernateRepositoryTest {

    @Autowired
    private TourService tourService;

    @Test
    public void findByCriteria1() {
        List<Tour> tours = tourService.findByCriteria(LocalDate.of(2018, Month.JUNE, 10), null, null,null, null, null);
        assertEquals(1, tours.size());
        assertEquals(new BigDecimal(243), tours.get(0).getCost());
    }

    @Test
    public void findByCriteria2() {
        List<Tour> tours = tourService.findByCriteria(null, Duration.ofDays(6), Country.SPAIN,null, TourType.WEEKEND, null);
        assertEquals(1, tours.size());
        Tour tour = tours.get(0);
        assertEquals(Duration.ofNanos(6), tour.getDuration());
        assertEquals(Country.SPAIN, tour.getCountry());
        assertEquals(TourType.WEEKEND, tour.getType());
    }

    @Test
    public void findByCriteria3() {
        List<Tour> tours = tourService.findByCriteria(null, null, null, Byte.valueOf("5"), null, null);
        assertEquals(1, tours.get(0).getHotel().getId());
        assertEquals("1234567", tours.get(0).getHotel().getPhone());
        assertEquals("hotel1", tours.get(0).getHotel().getName());
        assertEquals(tours.get(0).getHotel(), tours.get(1).getHotel());
        assertEquals(tours.get(1).getHotel(), tours.get(2).getHotel());
        assertEquals(tours.get(2).getHotel(), tours.get(3).getHotel());
    }

    @Test
    public void findAll() {
        List<Tour> tours = tourService.findAll();
        assertEquals(5, tours.size());
    }
}