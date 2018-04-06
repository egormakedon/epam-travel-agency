package com.epam.makedon.agency.repository.collection;

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
    private TourTypeCollectionRepository mockObj;

    @Before
    public void init() {
        mockObj = mock(TourTypeCollectionRepository.class);
        tourType = TourType.CHILDREN;
        TourTypeCollectionRepository.getInstance().remove(tourType);
    }

    @After
    public void destroy() {
        mockObj = null;
        TourTypeCollectionRepository.getInstance().remove(tourType);
        tourType = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(tourType)).thenReturn(true);
        assertEquals(mockObj.add(tourType), TourTypeCollectionRepository.getInstance().add(tourType));
        verify(mockObj).add(tourType);
    }

    @Test
    public void addFalseTest() {
        TourTypeCollectionRepository.getInstance().add(tourType);
        when(mockObj.add(tourType)).thenReturn(false);
        assertEquals(mockObj.add(tourType), TourTypeCollectionRepository.getInstance().add(tourType));
    }

    @Test
    public void getTrueTest() {
        TourTypeCollectionRepository.getInstance().add(tourType);
        when(mockObj.get(1)).thenReturn(Optional.of(tourType));
        assertEquals(mockObj.get(1), TourTypeCollectionRepository.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        TourTypeCollectionRepository.getInstance().add(tourType);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), TourTypeCollectionRepository.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        TourTypeCollectionRepository.getInstance().add(tourType);
        when(mockObj.remove(tourType)).thenReturn(true);
        assertEquals(mockObj.remove(tourType), TourTypeCollectionRepository.getInstance().remove(tourType));
        verify(mockObj).remove(tourType);
    }

    @Test
    public void removeFalseTest() {
        TourType c = TourType.WEEKEND;
        TourTypeCollectionRepository.getInstance().add(tourType);
        when(mockObj.remove(c)).thenReturn(false);
        assertEquals(mockObj.remove(c), TourTypeCollectionRepository.getInstance().remove(c));
        verify(mockObj).remove(c);
    }
}
