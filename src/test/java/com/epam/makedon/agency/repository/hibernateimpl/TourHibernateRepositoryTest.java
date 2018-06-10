package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.config.TestHibernateConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.HotelRepository;
import com.epam.makedon.agency.repository.TourRepository;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class TourHibernateRepositoryTest {

    @Autowired
    private TourRepository repository;

    @Autowired
    private HotelRepository hotelRepository;

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
}
