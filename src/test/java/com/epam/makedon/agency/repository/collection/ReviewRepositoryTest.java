package com.epam.makedon.agency.repository.collection;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.entity.impl.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewRepositoryTest {
    private static final long ID = 1;
    private static final Tour TOUR = new Tour();
    private static final User USER = new User();
    private static final String CONTENT = "content";

    private static Review review;

    @Mock
    private ReviewCollectionRepository mockObj;

    @Before
    public void init() {
        mockObj = mock(ReviewCollectionRepository.class);
        review = new Review();
        review.setId(ID);
        review.setTour(TOUR);
        review.setUser(USER);
        review.setContent(CONTENT);
        ReviewCollectionRepository.getInstance().remove(review);
    }

    @After
    public void destroy() {
        mockObj = null;
        ReviewCollectionRepository.getInstance().remove(review);
        review = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(review)).thenReturn(true);
        assertEquals(mockObj.add(review), ReviewCollectionRepository.getInstance().add(review));
        verify(mockObj).add(review);
    }

    @Test
    public void addFalseTest() {
        ReviewCollectionRepository.getInstance().add(review);
        when(mockObj.add(review)).thenReturn(false);
        assertEquals(mockObj.add(review), ReviewCollectionRepository.getInstance().add(review));
        verify(mockObj).add(review);
    }

    @Test
    public void getTrueTest() {
        ReviewCollectionRepository.getInstance().add(review);
        when(mockObj.get(1)).thenReturn(Optional.of(review));
        assertEquals(mockObj.get(1), ReviewCollectionRepository.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        ReviewCollectionRepository.getInstance().add(review);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), ReviewCollectionRepository.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        ReviewCollectionRepository.getInstance().add(review);
        when(mockObj.remove(review)).thenReturn(true);
        assertEquals(mockObj.remove(review), ReviewCollectionRepository.getInstance().remove(review));
        verify(mockObj).remove(review);
    }

    @Test
    public void removeFalseTest() {
        Review h = new Review();
        ReviewCollectionRepository.getInstance().add(review);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), ReviewCollectionRepository.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        ReviewCollectionRepository.getInstance().add(review);
        Review h = new Review();
        h.setId(ID);
        h.setTour(TOUR);
        h.setUser(USER);
        h.setContent("cccccc");
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), ReviewCollectionRepository.getInstance().update(h));
        verify(mockObj).update(h);
        ReviewCollectionRepository.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        ReviewCollectionRepository.getInstance().add(review);
        Review h = new Review();
        h.setId(3);
        h.setTour(TOUR);
        h.setUser(USER);
        h.setContent("cccccc");
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), ReviewCollectionRepository.getInstance().update(h));
        verify(mockObj).update(h);
        ReviewCollectionRepository.getInstance().remove(h);
    }
}
