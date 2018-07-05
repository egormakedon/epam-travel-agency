package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Test for {@link ReviewDatabaseRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ReviewDatabaseRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void addTrueTest() {
        Review review = Util.getReview();
        assertTrue(reviewRepository.add(review));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(reviewRepository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(reviewRepository.get(2).orElse(null));
    }

    @Test
    public void getTrueTest3() {
        Review review = Util.getReview();
        reviewRepository.add(review);
        assertEquals(reviewRepository.get(5).get().getId(), review.getId());
        assertEquals(reviewRepository.get(5).get().getContent(), review.getContent());
        assertEquals(reviewRepository.get(5).get().getTour(), review.getTour());
        assertEquals(reviewRepository.get(5).get().getVersion(), review.getVersion());
        assertEquals(reviewRepository.get(5).get().getUser().getTourList(), review.getUser().getTourList());
        assertEquals(reviewRepository.get(5).get().getUser().getLogin(), review.getUser().getLogin());
        assertEquals(reviewRepository.get(5).get().getUser().getPassword(), review.getUser().getPassword());
        assertEquals(reviewRepository.get(5).get().getUser().getReviewList(), review.getUser().getReviewList());
    }

    @Test
    public void getFalseTest() {
        Review review = Util.getReview();
        assertNotEquals(reviewRepository.get(1).orElse(null), review);
    }

    @Test
    public void removeTrueTest() {
        Review review = Util.getReview();
        assertTrue(reviewRepository.remove(review));
    }

    @Test
    public void removeFalseTest() {
        Review review = Util.getReview();
        review.setId(10);
        assertFalse(reviewRepository.remove(review));
    }

    @Test
    public void updateTrueTest() {
        Review review = Util.getReview();
        reviewRepository.add(review);
        review.setContent("hello");
        reviewRepository.update(review);
        assertEquals(review.getContent(), reviewRepository.get(5).orElseThrow(() -> new RuntimeException("")).getContent());
    }

    @Test
    public void updateFalseTest() {
        Review review = Util.getReview();
        review.setId(100);
        assertFalse(reviewRepository.update(review).isPresent());
    }
}