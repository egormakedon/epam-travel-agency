package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Test for {@link com.epam.makedon.agency.agencydomain.repository.hibernateimpl.HotelHibernateRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional

public class HotelHibernateRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    public void addTrueTest1() {
        Hotel hotel = Util.getHotel();
        assertTrue(hotelRepository.add(hotel));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(hotelRepository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        Hotel hotel = Util.getHotel();
        hotelRepository.add(hotel);
        assertEquals(hotelRepository.get(6).orElse(null), hotel);
    }

    @Test
    public void getFalseTest() {
        Hotel hotel = Util.getHotel();
        assertNotEquals(hotelRepository.get(1).orElse(null), hotel);
    }

    @Test
    public void removeTrueTest() {
        Hotel hotel = new Hotel();
        hotel.setId(1);
        assertTrue(hotelRepository.remove(hotel));
    }

    @Test
    public void removeFalseTest2() {
        Hotel hotel = new Hotel();
        hotel.setId(-1);
        assertFalse(hotelRepository.remove(hotel));
    }

    @Test
    public void updateTrueTest() {
        Hotel hotel = Util.getHotel();

        hotelRepository.add(hotel);
        hotel.setName("hello");
        hotelRepository.update(hotel);

        assertEquals(hotel, hotelRepository.get(6).orElse(null));
    }
}