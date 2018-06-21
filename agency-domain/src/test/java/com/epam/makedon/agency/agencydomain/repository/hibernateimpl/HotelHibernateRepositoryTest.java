package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
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

@RunWith(SpringRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class HotelHibernateRepositoryTest {
    private static final String NAME_LITERAL = "hotel4";
    private static final String PHONE_LITERAL = "2934";

    @Autowired
    private HotelRepository repository;

    @Test
    public void addTrueTest1() {
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName(NAME_LITERAL);
        hotel.setPhone(PHONE_LITERAL);
        hotel.setStars((byte)1);
        assertTrue(repository.add(hotel));
    }

    @Test
    public void addTrueTest2() {
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName("hotel");
        hotel.setPhone("hotel");
        hotel.setStars((byte)5);
        assertTrue(repository.add(hotel));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        Hotel hotel = new Hotel();
        hotel.setId(6);
        hotel.setName("hotel4");
        hotel.setPhone(PHONE_LITERAL);
        hotel.setStars((byte)4);
        repository.add(hotel);
        assertEquals(repository.get(6).orElse(null), hotel);
    }

    @Test
    public void getFalseTest() {
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName(NAME_LITERAL);
        hotel.setPhone(PHONE_LITERAL);
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
        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName(NAME_LITERAL);
        hotel.setPhone(PHONE_LITERAL);
        hotel.setStars((byte)4);

        repository.add(hotel);
        hotel.setName("hello");
        repository.update(hotel);

        assertEquals(hotel, repository.get(6).orElse(null));
    }
}