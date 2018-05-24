package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

public class TourDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static TourRepository repository;

    private static final long ID = 3;
    private static final LocalDate DATE = LocalDate.now();
    private static final Image IMAGE = null;
    private static final Duration DURATION = Duration.ofDays(10);
    private static final String DESCRIPTION = "desc";
    private static final BigDecimal COST = BigDecimal.valueOf(120);
    private static final Country COUNTRY = Country.SPAIN;
    private static final TourType TYPE = TourType.WEEKEND;
    private static final Hotel HOTEL = new Hotel();

    private static Tour tour;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        repository = context.getBean("tourDatabaseRepository", TourRepository.class);

        tour = new Tour();
        tour.setType(TYPE);
        tour.setCountry(COUNTRY);
        tour.setId(ID);
        HOTEL.setId(1);
        tour.setHotel(HOTEL);
        tour.setDuration(DURATION);
        tour.setDescription(DESCRIPTION);
        tour.setDate(DATE);
        tour.setCost(COST);
    }

    @After
    public void destroy() {
        ((EmbeddedDatabase)context.getBean("dataSource")).shutdown();
        context = null;
        repository = null;
        tour = null;
    }

    @Test
    public void addTrueTest() {
        assertTrue(repository.add(tour));
    }


    @Test
    public void getTrueTest() {
        repository.add(tour);
        Optional<Tour> opt = Optional.of(tour);
        assertEquals(repository.get(3).get().getId(), opt.get().getId());
    }

    @Test
    public void getFalseTest() {
        Optional<Tour> opt = Optional.of(tour);
        assertNotEquals(repository.get(1), opt.get());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        Optional<Tour> opt = repository.get(100);
        fail();
    }

    @Test
    public void removeTrueTest() {
        repository.add(tour);
        assertTrue(repository.remove(tour));
    }

    @Test
    public void removeFalseTest() {
        assertFalse(repository.remove(tour));
    }

    @Test
    public void updateTrueTest() {
        repository.add(tour);
        tour.setDescription("hello");
        repository.update(tour);
        Optional<Tour> opt = Optional.of(tour);
        assertEquals(opt.get().getDescription(), repository.get(3).get().getDescription());
    }

    @Test
    public void updateFalseTest() {
        tour.setId(100);
        assertFalse(repository.update(tour).isPresent());
    }
}
