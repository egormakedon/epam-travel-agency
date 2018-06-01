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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HotelServiceTest {

    @Autowired
    private HotelService service;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        service.add(null);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        service.remove(null);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        service.update(null);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest1() {
        service.get(0);
        fail();
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest2() {
        service.get(-3);
        fail();
    }

    @Test
    @Transactional
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
    @Transactional
    public void removeTrueTest() {
        Hotel h = new Hotel();
        h.setId(3);
        assertTrue(service.remove(h));
    }

    @Test
    @Transactional
    public void updateTrueTest() {
        Hotel h = new Hotel();
        h.setId(4);
        h.setName("name");
        h.setPhone("phone");

        service.add(h);
        h.setName("newName");
        service.update(h);

        Optional<Hotel> opt = service.get(4);
        assertEquals(h, opt.orElse(null));
    }
}
