package com.epam.makedon.agency.repository.databaseimpl;

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
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourDatabaseRepositoryTest {

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
        tour.setDescription("description");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(1));
        assertTrue(repository.add(tour));
    }


    @Test
    public void getTrueTest() {
        assertNotNull(repository.get(1).orElse(null));
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
        tour.setDescription("description");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(120));
        assertNotEquals(repository.get(1).orElse(null), tour);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        repository.get(100);
    }

    @Test
    public void removeTrueTest() {
        Tour tour = new Tour();
        tour.setId(1);
        assertTrue(repository.remove(tour));
    }

    @Test
    public void removeFalseTest() {
        Tour tour = new Tour();
        tour.setId(10);
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
        tour.setDescription("description");
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
