package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class ReviewDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static ReviewRepository repository;

    private static Review review;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("test.xml");
        repository = context.getBean("reviewDatabaseRepository", ReviewRepository.class);

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

    @After
    public void destroy() {
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        try {
            dataSource.getConnection().createStatement().execute("SHUTDOWN");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        context = null;
        repository = null;
        review = null;
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
