package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.config.MongodbConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for {@link ReviewMongodbRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MongodbConfiguration.class)

public class ReviewMongodbRepositoryTest {

    @Autowired
    private ReviewMongodbRepository reviewMongodbRepository;

    @Before
    public void before() {
        reviewMongodbRepository.deleteAll();
        inserting();
    }

    @After
    public void after() {
        reviewMongodbRepository.deleteAll();
    }

    @Test
    public void get() {
        Review review = reviewMongodbRepository.findById(0L).get();
        assertEquals("review1", review.getContent());
        assertEquals("user1", review.getUser().getLogin());
        assertEquals("user1", review.getUser().getPassword());
        assertEquals(Role.USER, review.getUser().getRole());
        assertEquals(Duration.ofDays(5), review.getTour().getDuration());
        assertEquals(Country.BELARUS, review.getTour().getCountry());
        assertEquals(new BigDecimal(1000), review.getTour().getCost());
    }

    @Test
    public void remove1() {
        assertEquals("review1", reviewMongodbRepository.findById(0L).get().getContent());
        reviewMongodbRepository.deleteById(0L);
        assertNull(reviewMongodbRepository.findById(0L).orElse(null));
    }

    @Test
    public void remove2() {
        assertEquals(1, reviewMongodbRepository.count());
        reviewMongodbRepository.deleteById(0L);
        assertNull(reviewMongodbRepository.findById(0L).orElse(null));
    }

    private void inserting() {
        Review review = new Review();
        review.setId(0L);
        review.setContent("review1");

        User user = new User();
        user.setLogin("user1");
        user.setPassword("user1");
        user.setId(0L);
        user.setRole(Role.USER);
        review.setUser(user);

        Tour tour = new Tour();
        tour.setId(0L);
        tour.setDuration(Duration.ofDays(5));
        tour.setCountry(Country.BELARUS);
        tour.setCost(new BigDecimal(1000));
        review.setTour(tour);
        reviewMongodbRepository.insert(review);
    }
}