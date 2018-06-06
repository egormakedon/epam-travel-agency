package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourHibernateRepositoryTest {

    @Autowired
    private TourRepository repository;

    @Test
    public void addTrueTest() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        tour.setId(3);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(2));
        tour.setDescription("123");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(1));
        assertTrue(repository.add(tour));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(repository.get(2).orElse(null));
    }

    @Test
    public void getFalseTest() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        tour.setId(3);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(10));
        tour.setDescription("desc");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(120));
        assertNotEquals(repository.get(1).orElse(null), tour);
    }

    @Test
    public void getExcTest1() {
        try {
            repository.get(100);
            fail();
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getExcTest2() {
        try {
            repository.get(-1);
            fail();
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void removeTrueTest1() {
        Tour tour = new Tour();
        tour.setId(1);
        assertTrue(repository.remove(tour));
    }

    @Test
    public void removeTrueTest2() {
        Tour tour = new Tour();
        tour.setId(2);
        assertTrue(repository.remove(tour));
    }

    @Test
    public void removeFalseTest1() {
        Tour tour = new Tour();
        tour.setId(10);
        assertFalse(repository.remove(tour));
    }

    @Test
    public void removeFalseTest2() {
        Tour tour = new Tour();
        tour.setId(-1);
        assertFalse(repository.remove(tour));
    }

    @Test
    public void updateTrueTest() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        tour.setId(3);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(10));
        tour.setDescription("sss");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(120));

        repository.add(tour);
        tour.setDescription("hello");
        repository.update(tour);

        assertEquals(tour.getDescription(), repository.get(3).orElseThrow(() -> new RuntimeException("")).getDescription());
    }

    @Test
    public void updateFalseTest() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(10));
        tour.setDescription("description");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(120));
        tour.setId(100);
        assertFalse(repository.update(tour).isPresent());
    }
}
