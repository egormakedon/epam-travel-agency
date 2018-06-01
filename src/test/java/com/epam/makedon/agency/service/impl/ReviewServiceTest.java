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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
public class ReviewServiceTest {

    @Autowired
    private ReviewService service;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        service.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        service.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        service.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest1() {
        service.get(0);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest2() {
        service.get(-3);
    }

    @Test
    @Transactional
    public void addTrueTest() {
        Review review = new Review();
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("content");
        assertTrue(service.add(review));
    }

    @Test
    public void getTrueTest() {
        Optional<Review> opt = service.get(1);
        assertNotNull(opt.orElse(null));
    }

    @Test
    @Transactional
    public void removeTrueTest() {
        Review r = new Review();
        r.setId(3);
        assertTrue(service.remove(r));
    }

    @Test
    @Transactional
    public void updateTrueTest() {
        Review r = new Review();
        r.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        r.setUser(user);
        r.setTour(tour);
        r.setContent("content");

        service.add(r);
        r.setContent("newContent");
        service.update(r);

        Optional<Review> opt = service.get(5);
        assertEquals(r, opt.orElse(null));
    }
}
