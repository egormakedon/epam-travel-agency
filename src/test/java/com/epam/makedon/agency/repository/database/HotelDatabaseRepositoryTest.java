package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.entity.impl.Hotel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.Optional;

import static org.junit.Assert.*;

public class HotelDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static HotelDatabaseRepository repository;

    private static final long ID = 4;
    private static final String NAME = "name";
    private static final byte STARS = 5;
    private static final String PHONE = "12345";

    private static Hotel hotel;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        repository = context.getBean("hotelDatabaseRepository", HotelDatabaseRepository.class);

        hotel = new Hotel();
        hotel.setId(ID);
        hotel.setName(NAME);
        hotel.setPhone(PHONE);
        hotel.setStars(STARS);
    }

    @After
    public void destroy() {
        ((EmbeddedDatabase)context.getBean("dataSource")).shutdown();
        context = null;
        repository = null;
        hotel = null;
    }

    @Test
    public void addTrueTest() {
        assertTrue(repository.add(hotel));
    }


    @Test
    public void getTrueTest() {
        repository.add(hotel);
        hotel.setId(4);
        Optional<Hotel> opt = Optional.of(hotel);
        assertEquals(repository.get(4).get(), opt.get());
    }

    @Test
    public void getFalseTest() {
        Optional<Hotel> opt = Optional.of(hotel);
        assertNotEquals(repository.get(1), opt.get());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        Optional<Hotel> opt = repository.get(100);
        fail();
    }

    @Test
    public void removeTrueTest() {
        repository.add(hotel);
        hotel.setId(4);
        assertTrue(repository.remove(hotel));
    }

    @Test
    public void removeExcTest() {
        assertFalse(repository.remove(hotel));
    }

    @Test
    public void updateTrueTest() {
        repository.add(hotel);
        hotel.setName("hello");
        repository.update(hotel);
        Optional<Hotel> opt = Optional.of(hotel);
        assertEquals(opt.get(), repository.get(4).get());
    }

    @Test
    public void updateFalseTest() {
        hotel.setId(100);
        assertFalse(repository.update(hotel).isPresent());
    }
}
