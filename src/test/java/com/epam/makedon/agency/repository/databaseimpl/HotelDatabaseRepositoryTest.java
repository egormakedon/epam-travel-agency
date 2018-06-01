package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestConfiguration.class)
public class HotelDatabaseRepositoryTest {

    @Autowired
    private HotelRepository repository;

    @Autowired
    private DataSource dataSource;

    private static final long ID = 4;
    private static final String NAME = "name";
    private static final byte STARS = 5;
    private static final String PHONE = "12345";

    private static Hotel hotel;

    static {
        hotel = new Hotel();
        hotel.setId(ID);
        hotel.setName(NAME);
        hotel.setPhone(PHONE);
        hotel.setStars(STARS);
    }

    @After
    public void destroy() {
//        ((EmbeddedDatabase)dataSource).shutdown();
    }

    @Test
    public void addTrueTest() {
        assertTrue(repository.add(hotel));
    }

    @Test
    public void getTrueTest() {
        repository.add(hotel);
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
