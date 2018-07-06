package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link ReviewHibernateRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional

public class ReviewHibernateRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void removeTrueTest() {
        Review review = new Review();
        review.setId(1);
        assertTrue(reviewRepository.remove(review));
    }

    @Test
    public void removeFalseTest() {
        Review review = new Review();
        review.setId(-1);
        assertFalse(reviewRepository.remove(review));
    }
}