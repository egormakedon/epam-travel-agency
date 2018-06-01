package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourServiceTest {

    @Autowired
    private TourService service;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        service.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        service.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        service.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest1() {
        service.get(0);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest2() {
        service.get(-3);
    }

    @Test
    @Transactional
    public void addTrueTest() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        tour.setId(3);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(10));
        tour.setDescription("description");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(120));
        assertTrue(service.add(tour));
    }

    @Test
    public void getTrueTest() {
        Optional<Tour> opt = service.get(1);
        assertNotNull(opt.orElse(null));
    }

    @Test
    @Transactional
    public void removeTrueTest() {
        Tour t = new Tour();
        t.setId(2);
        assertTrue(service.remove(t));
    }

    @Test
    @Transactional
    public void updateTrueTest() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        tour.setId(3);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(10));
        tour.setDescription("description");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(120));

        service.add(tour);
        tour.setDescription("newDescription");
        service.update(tour);

        Optional<Tour> opt = service.get(3);
        assertEquals(tour, opt.orElse(null));
    }
}
