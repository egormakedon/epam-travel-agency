package com.epam.makedon.agency.repository.impl;

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
    private ReviewRepositoryImpl mockObj;

    @Before
    public void init() {
        mockObj = mock(ReviewRepositoryImpl.class);
        review = new Review();
        review.setId(ID);
        review.setTour(TOUR);
        review.setUser(USER);
        review.setContent(CONTENT);
        ReviewRepositoryImpl.getInstance().remove(review);
    }

    @After
    public void destroy() {
        mockObj = null;
        ReviewRepositoryImpl.getInstance().remove(review);
        review = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(review)).thenReturn(true);
        assertEquals(mockObj.add(review), ReviewRepositoryImpl.getInstance().add(review));
        verify(mockObj).add(review);
    }

    @Test
    public void addFalseTest() {
        ReviewRepositoryImpl.getInstance().add(review);
        when(mockObj.add(review)).thenReturn(false);
        assertEquals(mockObj.add(review), ReviewRepositoryImpl.getInstance().add(review));
        verify(mockObj).add(review);
    }

    @Test
    public void getTrueTest() {
        ReviewRepositoryImpl.getInstance().add(review);
        when(mockObj.get(1)).thenReturn(Optional.of(review));
        assertEquals(mockObj.get(1), ReviewRepositoryImpl.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        ReviewRepositoryImpl.getInstance().add(review);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), ReviewRepositoryImpl.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        ReviewRepositoryImpl.getInstance().add(review);
        when(mockObj.remove(review)).thenReturn(true);
        assertEquals(mockObj.remove(review), ReviewRepositoryImpl.getInstance().remove(review));
        verify(mockObj).remove(review);
    }

    @Test
    public void removeFalseTest() {
        Review h = new Review();
        ReviewRepositoryImpl.getInstance().add(review);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), ReviewRepositoryImpl.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        ReviewRepositoryImpl.getInstance().add(review);
        Review h = new Review();
        h.setId(ID);
        h.setTour(TOUR);
        h.setUser(USER);
        h.setContent("cccccc");
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), ReviewRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        ReviewRepositoryImpl.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        ReviewRepositoryImpl.getInstance().add(review);
        Review h = new Review();
        h.setId(3);
        h.setTour(TOUR);
        h.setUser(USER);
        h.setContent("cccccc");
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), ReviewRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        ReviewRepositoryImpl.getInstance().remove(h);
    }
}
