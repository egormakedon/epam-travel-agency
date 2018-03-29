package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.TourType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TourTypeRepositoryTest {
    private static TourType tourType;

    @Mock
    private TourTypeRepositoryImpl mockObj;

    @Before
    public void init() {
        mockObj = mock(TourTypeRepositoryImpl.class);
        tourType = TourType.CHILDREN;
        TourTypeRepositoryImpl.getInstance().remove(tourType);
    }

    @After
    public void destroy() {
        mockObj = null;
        TourTypeRepositoryImpl.getInstance().remove(tourType);
        tourType = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(tourType)).thenReturn(true);
        assertEquals(mockObj.add(tourType), TourTypeRepositoryImpl.getInstance().add(tourType));
        verify(mockObj).add(tourType);
    }

    @Test
    public void addFalseTest() {
        TourTypeRepositoryImpl.getInstance().add(tourType);
        when(mockObj.add(tourType)).thenReturn(false);
        assertEquals(mockObj.add(tourType), TourTypeRepositoryImpl.getInstance().add(tourType));
    }

    @Test
    public void getTrueTest() {
        TourTypeRepositoryImpl.getInstance().add(tourType);
        when(mockObj.get(1)).thenReturn(Optional.of(tourType));
        assertEquals(mockObj.get(1), TourTypeRepositoryImpl.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        TourTypeRepositoryImpl.getInstance().add(tourType);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), TourTypeRepositoryImpl.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        TourTypeRepositoryImpl.getInstance().add(tourType);
        when(mockObj.remove(tourType)).thenReturn(true);
        assertEquals(mockObj.remove(tourType), TourTypeRepositoryImpl.getInstance().remove(tourType));
        verify(mockObj).remove(tourType);
    }

    @Test
    public void removeFalseTest() {
        TourType c = TourType.WEEKEND;
        TourTypeRepositoryImpl.getInstance().add(tourType);
        when(mockObj.remove(c)).thenReturn(false);
        assertEquals(mockObj.remove(c), TourTypeRepositoryImpl.getInstance().remove(c));
        verify(mockObj).remove(c);
    }
}
