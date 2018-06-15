package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
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
public class ReviewHibernateRepositoryTest {

    @Autowired
    private ReviewRepository repository;

    @Test
    public void getTrueTest1() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(repository.get(2).orElse(null));
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
}
