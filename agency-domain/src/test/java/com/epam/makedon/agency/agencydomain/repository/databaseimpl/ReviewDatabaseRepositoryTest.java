package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReviewDatabaseRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    @Test
    public void addTrueTest() {
        Review review = new Review();
        review.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("ll");
        assertTrue(repository.add(review));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(repository.get(2).orElse(null));
    }

    @Test
    public void getTrueTest3() {
        Review review = new Review();
        review.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("llss");
        repository.add(review);
        assertEquals(repository.get(5).orElse(null), review);
    }

    @Test
    public void getFalseTest() {
        Review review = new Review();
        review.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("ll");
        assertNotEquals(repository.get(1).orElse(null), review);
    }

    @Test
    public void getExcTest1() {
        try {
            repository.get(100);
            fail();
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getExcTest2() {
        try {
            repository.get(-1);
            fail();
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void removeTrueTest() {
        Review review = new Review();
        review.setId(1);
        assertTrue(repository.remove(review));
    }

    @Test
    public void removeFalseTest() {
        Review review = new Review();
        review.setId(10);
        assertFalse(repository.remove(review));
    }

    @Test
    public void updateTrueTest() {
        Review review = new Review();
        review.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("123");

        repository.add(review);
        review.setContent("hello");
        repository.update(review);

        assertEquals(review.getContent(), repository.get(5).orElseThrow(() -> new RuntimeException("")).getContent());
    }

    @Test
    public void updateFalseTest() {
        Review review = new Review();
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("ll33");
        review.setId(100);
        assertFalse(repository.update(review).isPresent());
    }
}
