package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.config.Util;
import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.service.ReviewService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

/**
 * Test for {@link ReviewServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        reviewService.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        reviewService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        reviewService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        reviewService.get(-100);
    }

    @Test
    public void addTrueTest1() {
        Review review = Util.getReview();
        assertTrue(reviewService.add(review));
    }

    @Test
    public void addTrueTest2() {
        Review review = Util.getReview();
        assertTrue(reviewService.add(review));
        review.setId(review.getId() + 1);
        assertTrue(reviewService.add(review));
        review.setId(review.getId() + 1);
        assertTrue(reviewService.add(review));
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(reviewService.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(reviewService.get(2).orElse(null));
    }

    @Test
    public void getTrueTest3() {
        Review review = Util.getReview();
        reviewService.add(review);
        assertEquals(reviewService.get(5).get().getId(), review.getId());
        assertEquals(reviewService.get(5).get().getContent(), review.getContent());
        assertEquals(reviewService.get(5).get().getTour(), review.getTour());
        assertEquals(reviewService.get(5).get().getVersion(), review.getVersion());
        assertEquals(reviewService.get(5).get().getUser().getTourList(), review.getUser().getTourList());
        assertEquals(reviewService.get(5).get().getUser().getLogin(), review.getUser().getLogin());
        assertEquals(reviewService.get(5).get().getUser().getPassword(), review.getUser().getPassword());
        assertEquals(reviewService.get(5).get().getUser().getReviewList(), review.getUser().getReviewList());
    }

    @Test
    public void getFalseTest() {
        Review review = Util.getReview();
        assertNotEquals(reviewService.get(1).orElse(null), review);
    }

    @Test
    public void removeTrueTest() {
        Review review = Util.getReview();
        assertTrue(reviewService.remove(review));
    }

    @Test
    public void removeFalseTest() {
        Review review = Util.getReview();
        review.setId(10);
        assertFalse(reviewService.remove(review));
    }

    @Test
    public void updateTrueTest() {
        Review review = Util.getReview();
        reviewService.add(review);
        review.setContent("hello");
        reviewService.update(review);
        assertEquals(review.getContent(), reviewService.get(5).orElseThrow(() -> new RuntimeException("")).getContent());
    }

    @Test
    public void updateFalseTest() {
        Review review = Util.getReview();
        review.setId(100);
        assertFalse(reviewService.update(review).isPresent());
    }
}