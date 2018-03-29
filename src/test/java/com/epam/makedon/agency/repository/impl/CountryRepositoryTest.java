package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.Country;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class CountryRepositoryTest {
    private static Country country;

    @Mock
    private CountryRepositoryImpl mockObj;

    @Before
    public void init() {
        mockObj = mock(CountryRepositoryImpl.class);
        country = Country.BELARUS;
        CountryRepositoryImpl.getInstance().remove(country);
    }

    @After
    public void destroy() {
        mockObj = null;
        CountryRepositoryImpl.getInstance().remove(country);
        country = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(country)).thenReturn(true);
        assertEquals(mockObj.add(country), CountryRepositoryImpl.getInstance().add(country));
        verify(mockObj).add(country);
    }

    @Test
    public void addFalseTest() {
        CountryRepositoryImpl.getInstance().add(country);
        when(mockObj.add(country)).thenReturn(false);
        assertEquals(mockObj.add(country), CountryRepositoryImpl.getInstance().add(country));
    }

    @Test
    public void getTrueTest() {
        CountryRepositoryImpl.getInstance().add(country);
        when(mockObj.get(1)).thenReturn(Optional.of(country));
        assertEquals(mockObj.get(1), CountryRepositoryImpl.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        CountryRepositoryImpl.getInstance().add(country);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), CountryRepositoryImpl.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        CountryRepositoryImpl.getInstance().add(country);
        when(mockObj.remove(country)).thenReturn(true);
        assertEquals(mockObj.remove(country), CountryRepositoryImpl.getInstance().remove(country));
        verify(mockObj).remove(country);
    }

    @Test
    public void removeFalseTest() {
        Country c = Country.CHINA;
        CountryRepositoryImpl.getInstance().add(country);
        when(mockObj.remove(c)).thenReturn(false);
        assertEquals(mockObj.remove(c), CountryRepositoryImpl.getInstance().remove(c));
        verify(mockObj).remove(c);
    }
}