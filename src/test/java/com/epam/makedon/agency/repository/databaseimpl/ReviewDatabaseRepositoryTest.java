package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Review;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
public class ReviewDatabaseRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    private Review review;

    @Before
    public void init() {
        review = new Review();
        review.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(1);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("ll");
    }

    @Test
    public void addTrueTest() {
        assertTrue(repository.add(review));
    }


    @Test
    public void getTrueTest() {
        repository.add(review);
        Optional<Review> opt = Optional.of(review);
        assertEquals(repository.get(5).get().getTour().getDescription(), opt.get().getTour().getDescription());
    }

    @Test
    public void getFalseTest() {
        Optional<Review> opt = Optional.of(review);
        assertNotEquals(repository.get(1), opt.get());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        Optional<Review> opt = repository.get(100);
        fail();
    }

    @Test
    public void removeTrueTest() {
        repository.add(review);
        assertTrue(repository.remove(review));
    }

    @Test
    public void removeFalseTest() {
        assertFalse(repository.remove(review));
    }

    @Test
    public void updateTrueTest() {
        repository.add(review);
        review.setContent("hello");
        repository.update(review);
        Optional<Review> opt = Optional.of(review);
        assertEquals(opt.get().getContent(), repository.get(5).get().getContent());
    }

    @Test
    public void updateFalseTest() {
        review.setId(100);
        assertFalse(repository.update(review).isPresent());
    }
}
