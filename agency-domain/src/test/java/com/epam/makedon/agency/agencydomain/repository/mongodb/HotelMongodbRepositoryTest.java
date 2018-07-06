package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.config.MongodbConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for {@link HotelMongodbRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MongodbConfiguration.class)

public class HotelMongodbRepositoryTest {

    @Autowired
    private HotelMongodbRepository hotelMongodbRepository;

    @Before
    public void before() {
        hotelMongodbRepository.deleteAll();
        inserting();
    }

    @After
    public void after() {
        hotelMongodbRepository.deleteAll();
    }

    @Test
    public void add1() {
        Hotel hotel = Util.getHotel();
        hotelMongodbRepository.insert(hotel);
        assertEquals(hotel, hotelMongodbRepository.findById(4L).orElse(null));
    }

    @Test
    public void add2() {
        Hotel hotel = Util.getHotel();
        hotelMongodbRepository.insert(hotel);
        assertEquals(hotel, hotelMongodbRepository.findById(4L).orElse(null));
        hotel.setId(5L);
        hotelMongodbRepository.insert(hotel);
        assertEquals(hotel, hotelMongodbRepository.findById(5L).orElse(null));
        hotel.setId(6L);
        hotelMongodbRepository.insert(hotel);
        assertEquals(hotel, hotelMongodbRepository.findById(6L).orElse(null));
    }

    @Test
    public void count() {
        assertEquals(3, hotelMongodbRepository.count());
    }

    @Test
    public void get() {
        assertEquals("1", hotelMongodbRepository.findById(0L).get().getPhone());
        assertEquals("2", hotelMongodbRepository.findById(1L).get().getPhone());
        assertEquals("3", hotelMongodbRepository.findById(2L).get().getPhone());
    }

    @Test
    public void removeAll() {
        assertEquals(3, hotelMongodbRepository.count());
        hotelMongodbRepository.deleteAll();
        assertEquals(0, hotelMongodbRepository.count());
    }

    @Test
    public void remove() {
        assertEquals("hotel2", hotelMongodbRepository.findById(1L).get().getName());
        hotelMongodbRepository.deleteById(1L);
        assertNull(hotelMongodbRepository.findById(1L).orElse(null));
    }

    @Test
    public void update() {
        assertEquals("hotel2", hotelMongodbRepository.findById(1L).get().getName());
        Hotel hotel = hotelMongodbRepository.findById(1L).orElse(null);
        hotel.setName("XXX");
        hotelMongodbRepository.save(hotel);
        assertEquals("XXX", hotelMongodbRepository.findById(1L).get().getName());
    }

    private void inserting() {
        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        Hotel hotel3 = new Hotel();

        hotel1.setId(0L);
        hotel1.setName("hotel1");
        hotel1.setPhone("1");

        hotel2.setId(1L);
        hotel2.setName("hotel2");
        hotel2.setPhone("2");

        hotel3.setId(2L);
        hotel3.setName("hotel3");
        hotel3.setPhone("3");

        hotelMongodbRepository.insert(Arrays.asList(hotel1, hotel2, hotel3));
    }
}