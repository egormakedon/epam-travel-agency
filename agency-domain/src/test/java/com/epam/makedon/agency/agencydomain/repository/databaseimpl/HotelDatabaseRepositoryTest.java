package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.repository.HotelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Test for {@link HotelDatabaseRepository} class
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class HotelDatabaseRepositoryTest {

    @Autowired
    private HotelRepository repository;

    @Test
    public void addTrueTest1() {
        Hotel hotel = Util.getHotel();
        assertTrue(repository.add(hotel));
    }

    @Test
    public void addTrueTest2() {
        Hotel hotel = Util.getHotel();
        hotel.setName("hotel");
        hotel.setPhone("hotel");
        assertTrue(repository.add(hotel));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        Hotel hotel = Util.getHotel();
        repository.add(hotel);
        assertEquals(repository.get(4).orElse(null), hotel);
    }

    @Test
    public void getFalseTest() {
        Hotel hotel = Util.getHotel();
        assertNotEquals(repository.get(1).orElse(null), hotel);
    }

    @Test
    public void removeTrueTest1() {
        Hotel hotel = new Hotel();
        hotel.setId(1);
        assertTrue(repository.remove(hotel));
    }

    @Test
    public void removeTrueTest2() {
        Hotel hotel = new Hotel();
        hotel.setId(2);
        assertTrue(repository.remove(hotel));
    }

    @Test
    public void removeTrueTest3() {
        Hotel hotel = new Hotel();
        hotel.setId(3);
        assertTrue(repository.remove(hotel));
    }

    @Test
    public void removeFalseTest1() {
        Hotel hotel = new Hotel();
        hotel.setId(100);
        assertFalse(repository.remove(hotel));
    }

    @Test
    public void removeFalseTest2() {
        Hotel hotel = new Hotel();
        hotel.setId(-1);
        assertFalse(repository.remove(hotel));
    }

    @Test
    public void updateTrueTest() {
        Hotel hotel = Util.getHotel();
        repository.add(hotel);
        hotel.setName("hello");
        repository.update(hotel);
        assertEquals(hotel, repository.get(4).orElse(null));
    }

    @Test
    public void updateFalseTest() {
        Hotel hotel = Util.getHotel();
        hotel.setId(100);
        assertFalse(repository.update(hotel).isPresent());
    }
}