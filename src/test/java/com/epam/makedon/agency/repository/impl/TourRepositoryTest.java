package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.Tour;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TourRepositoryTest {
    private static final long ID = 1;
    private static final int COST = 10;

    private static Tour tour;

    @Mock
    private TourRepositoryImpl mockObj;

    @Before
    public void init() {
        mockObj = mock(TourRepositoryImpl.class);
        tour = new Tour();
        tour.setId(ID);
        tour.setCost(COST);
        TourRepositoryImpl.getInstance().remove(tour);
    }

    @After
    public void destroy() {
        mockObj = null;
        TourRepositoryImpl.getInstance().remove(tour);
        tour = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(tour)).thenReturn(true);
        assertEquals(mockObj.add(tour), TourRepositoryImpl.getInstance().add(tour));
        verify(mockObj).add(tour);
    }

    @Test
    public void addFalseTest() {
        TourRepositoryImpl.getInstance().add(tour);
        when(mockObj.add(tour)).thenReturn(false);
        assertEquals(mockObj.add(tour), TourRepositoryImpl.getInstance().add(tour));
        verify(mockObj).add(tour);
    }

    @Test
    public void getTrueTest() {
        TourRepositoryImpl.getInstance().add(tour);
        when(mockObj.get(1)).thenReturn(Optional.of(tour));
        assertEquals(mockObj.get(1), TourRepositoryImpl.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        TourRepositoryImpl.getInstance().add(tour);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), TourRepositoryImpl.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        TourRepositoryImpl.getInstance().add(tour);
        when(mockObj.remove(tour)).thenReturn(true);
        assertEquals(mockObj.remove(tour), TourRepositoryImpl.getInstance().remove(tour));
        verify(mockObj).remove(tour);
    }

    @Test
    public void removeFalseTest() {
        Tour h = new Tour();
        TourRepositoryImpl.getInstance().add(tour);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), TourRepositoryImpl.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        TourRepositoryImpl.getInstance().add(tour);
        Tour h = new Tour();
        h.setId(ID);
        h.setCost(20);
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), TourRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        TourRepositoryImpl.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        TourRepositoryImpl.getInstance().add(tour);
        Tour h = new Tour();
        h.setId(3);
        h.setCost(20);
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), TourRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        TourRepositoryImpl.getInstance().remove(h);
    }
}
