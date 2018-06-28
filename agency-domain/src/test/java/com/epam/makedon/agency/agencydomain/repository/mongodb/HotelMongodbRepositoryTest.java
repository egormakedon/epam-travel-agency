package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.config.MongodbConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MongodbConfiguration.class)
public class HotelMongodbRepositoryTest {

    @Autowired
    private HotelMongodbRepository repository;

    @Before
    public void before() {
        repository.deleteAll();
        inserting();
    }

    @Test
    public void add1() {
        Hotel hotel = new Hotel();
        hotel.setName("hotel");
        hotel.setStars((byte)5);
        hotel.setId(3L);
        repository.insert(hotel);
        assertEquals(hotel, repository.findById(3L).orElse(null));
    }

    @Test
    public void add2() {
        Hotel hotel = new Hotel();
        hotel.setId(3L);
        hotel.setName("hotel");
        hotel.setStars((byte)5);
        repository.insert(hotel);
        assertEquals(hotel, repository.findById(3L).orElse(null));
        hotel.setId(4L);
        repository.insert(hotel);
        assertEquals(hotel, repository.findById(4L).orElse(null));
        hotel.setId(5L);
        repository.insert(hotel);
        assertEquals(hotel, repository.findById(5L).orElse(null));
    }

    @Test
    public void count() {
        assertEquals(3, repository.count());
    }

    @Test
    public void get() {
        assertEquals("1", repository.findById(0L).get().getPhone());
        assertEquals("2", repository.findById(1L).get().getPhone());
        assertEquals("3", repository.findById(2L).get().getPhone());
    }

    @Test
    public void removeAll() {
        assertEquals(3, repository.count());
        repository.deleteAll();
        assertEquals(0, repository.count());
    }

    @Test
    public void remove() {
        assertEquals("hotel2", repository.findById(1L).get().getName());
        repository.deleteById(1L);
        assertNull(repository.findById(1L).orElse(null));
    }

    @Test
    public void update() {
        assertEquals("hotel2", repository.findById(1L).get().getName());
        Hotel hotel = repository.findById(1L).orElse(null);
        hotel.setName("XXX");
        repository.save(hotel);
        assertEquals("XXX", repository.findById(1L).get().getName());
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

        repository.insert(Arrays.asList(hotel1, hotel2, hotel3));
    }
}