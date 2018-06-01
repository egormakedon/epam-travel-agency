package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HotelDatabaseRepositoryTest {

    @Autowired
    private HotelRepository repository;

    @Test
    public void addTrueTest() {
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName("name");
        hotel.setPhone("12345");
        assertTrue(repository.add(hotel));
    }

    @Test
    public void getTrueTest() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getFalseTest() {
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName("name");
        hotel.setPhone("12345");
        assertNotEquals(repository.get(1).orElse(null), hotel);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        repository.get(100);
    }

    @Test
    public void removeTrueTest() {
        Hotel hotel = new Hotel();
        hotel.setId(1);
        assertTrue(repository.remove(hotel));
    }

    @Test
    public void removeExcTest() {
        Hotel hotel = new Hotel();
        hotel.setId(100);
        assertFalse(repository.remove(hotel));
    }

    @Test
    public void updateTrueTest() {
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName("name");
        hotel.setPhone("12345");

        repository.add(hotel);
        hotel.setName("hello");
        repository.update(hotel);

        assertEquals(hotel, repository.get(4).orElse(null));
    }

    @Test
    public void updateFalseTest() {
        Hotel hotel = new Hotel();
        hotel.setName("name");
        hotel.setPhone("12345");
        hotel.setId(100);
        assertFalse(repository.update(hotel).isPresent());
    }
}
