package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Review;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.service.ReviewService;
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
public class ReviewServiceTest {

    @Autowired
    private ReviewService service;

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
        Review review = new Review();
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("content123");
        assertTrue(service.add(review));
    }

    @Test
    public void getTrueTest() {
        Optional<Review> opt = service.get(1);
        assertNotNull(opt.orElse(null));
    }

    @Test
    public void removeTrueTest() {
        Review r = new Review();
        r.setId(3);
        assertTrue(service.remove(r));
    }

    @Test
    public void updateTrueTest() {
        Review r = new Review();
        r.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        r.setUser(user);
        r.setTour(tour);
        r.setContent("zzzz");

        service.add(r);
        r.setContent("newContent");
        service.update(r);

        Optional<Review> opt = service.get(5);
        assertEquals(r, opt.orElse(null));
    }
}
