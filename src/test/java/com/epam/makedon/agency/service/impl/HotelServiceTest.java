package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.service.HotelService;
import com.epam.makedon.agency.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HotelServiceTest {

    @Autowired
    private HotelService service;

    @Test
    public void exceptionAddTest() {
        try {
            service.add(null);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionRemoveTest() {
        try {
            service.remove(null);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionUpdateTest() {
        try {
            service.update(null);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionGetTest1() {
        try {
            service.get(0);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void exceptionGetTest2() {
        try {
            service.get(-3);
            fail();
        } catch (ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void addTrueTest() {
        Hotel h = new Hotel();
        h.setName("hotel");
        h.setPhone("phone");
        assertTrue(service.add(h));
    }

    @Test
    public void getTrueTest() {
       Optional<Hotel> opt = service.get(1);
       assertNotNull(opt.orElse(null));
    }

    @Test
    public void removeTrueTest() {
        Hotel h = new Hotel();
        h.setId(3);
        assertTrue(service.remove(h));
    }

    @Test
    public void updateTrueTest() {
        Hotel h = new Hotel();
        h.setId(4);
        h.setName("name");
        h.setPhone("1234");

        service.add(h);
        h.setName("newName");
        service.update(h);

        Optional<Hotel> opt = service.get(4);
        assertEquals(h, opt.orElse(null));
    }
}
