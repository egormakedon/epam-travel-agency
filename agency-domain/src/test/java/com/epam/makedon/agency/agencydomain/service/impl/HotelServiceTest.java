package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.service.HotelService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Test for {@link HotelServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        hotelService.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        hotelService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        hotelService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        hotelService.get(-10);
    }

    @Test
    public void addTrueTest1() {
        Hotel hotel = Util.getHotel();
        assertTrue(hotelService.add(hotel));
    }

    @Test
    public void addTrueTest2() {
        Hotel hotel = Util.getHotel();
        hotel.setName("hotel");
        hotel.setPhone("hotel");
        assertTrue(hotelService.add(hotel));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(hotelService.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        Hotel hotel = Util.getHotel();
        hotelService.add(hotel);
        assertEquals(hotelService.get(4).orElse(null), hotel);
    }

    @Test
    public void getFalseTest() {
        Hotel hotel = Util.getHotel();
        assertNotEquals(hotelService.get(1).orElse(null), hotel);
    }

    @Test
    public void removeTrueTest() {
        Hotel hotel = Util.getHotel();
        hotel.setId(1);
        assertTrue(hotelService.remove(hotel));
    }

    @Test
    public void removeFalseTest1() {
        Hotel hotel = Util.getHotel();
        hotel.setId(-1);
        assertFalse(hotelService.remove(hotel));
    }

    @Test
    public void updateTrueTest() {
        Hotel hotel = Util.getHotel();
        hotelService.add(hotel);
        hotel.setName("hello");
        hotelService.update(hotel);
        assertEquals(hotel, hotelService.get(4).orElse(null));
    }

    @Test
    public void updateFalseTest() {
        Hotel hotel = Util.getHotel();
        hotel.setId(100);
        assertFalse(hotelService.update(hotel).isPresent());
    }
}