package com.epam.makedon.agency.repository.collection;

import com.epam.makedon.agency.entity.impl.Tour;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TourRepositoryTest {
    private static final long ID = 1;
    private static final BigDecimal COST = BigDecimal.valueOf(10);

    private static Tour tour;

    @Mock
    private TourCollectionRepository mockObj;

    @Before
    public void init() {
        mockObj = mock(TourCollectionRepository.class);
        tour = new Tour();
        tour.setId(ID);
        tour.setCost(COST);
        TourCollectionRepository.getInstance().remove(tour);
    }

    @After
    public void destroy() {
        mockObj = null;
        TourCollectionRepository.getInstance().remove(tour);
        tour = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(tour)).thenReturn(true);
        assertEquals(mockObj.add(tour), TourCollectionRepository.getInstance().add(tour));
        verify(mockObj).add(tour);
    }

    @Test
    public void addFalseTest() {
        TourCollectionRepository.getInstance().add(tour);
        when(mockObj.add(tour)).thenReturn(false);
        assertEquals(mockObj.add(tour), TourCollectionRepository.getInstance().add(tour));
        verify(mockObj).add(tour);
    }

    @Test
    public void getTrueTest() {
        TourCollectionRepository.getInstance().add(tour);
        when(mockObj.get(1)).thenReturn(Optional.of(tour));
        assertEquals(mockObj.get(1), TourCollectionRepository.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        TourCollectionRepository.getInstance().add(tour);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), TourCollectionRepository.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        TourCollectionRepository.getInstance().add(tour);
        when(mockObj.remove(tour)).thenReturn(true);
        assertEquals(mockObj.remove(tour), TourCollectionRepository.getInstance().remove(tour));
        verify(mockObj).remove(tour);
    }

    @Test
    public void removeFalseTest() {
        Tour h = new Tour();
        TourCollectionRepository.getInstance().add(tour);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), TourCollectionRepository.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        TourCollectionRepository.getInstance().add(tour);
        Tour h = new Tour();
        h.setId(ID);
        h.setCost(BigDecimal.valueOf(10));
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), TourCollectionRepository.getInstance().update(h));
        verify(mockObj).update(h);
        TourCollectionRepository.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        TourCollectionRepository.getInstance().add(tour);
        Tour h = new Tour();
        h.setId(3);
        h.setCost(BigDecimal.valueOf(10));
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), TourCollectionRepository.getInstance().update(h));
        verify(mockObj).update(h);
        TourCollectionRepository.getInstance().remove(h);
    }
}
