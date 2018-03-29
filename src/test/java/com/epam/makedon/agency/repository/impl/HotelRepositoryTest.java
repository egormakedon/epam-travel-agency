package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HotelRepositoryTest {
    private static final long ID = 1;
    private static final String NAME = "name";
    private static final byte STARS = 5;
    private static final String PHONE = "12345";
    private static final Country COUNTRY = Country.BELARUS;

    private static Hotel hotel;

    @Mock
    private HotelRepositoryImpl mockObj;

    @Before
    public void init() {
        mockObj = mock(HotelRepositoryImpl.class);
        hotel = new Hotel();
        hotel.setId(ID);
        hotel.setName(NAME);
        hotel.setCountry(COUNTRY);
        hotel.setPhone(PHONE);
        hotel.setStars(STARS);
        HotelRepositoryImpl.getInstance().remove(hotel);
    }

    @After
    public void destroy() {
        mockObj = null;
        HotelRepositoryImpl.getInstance().remove(hotel);
        hotel = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(hotel)).thenReturn(true);
        assertEquals(mockObj.add(hotel), HotelRepositoryImpl.getInstance().add(hotel));
        verify(mockObj).add(hotel);
    }

    @Test
    public void addFalseTest() {
        HotelRepositoryImpl.getInstance().add(hotel);
        when(mockObj.add(hotel)).thenReturn(false);
        assertEquals(mockObj.add(hotel), HotelRepositoryImpl.getInstance().add(hotel));
        verify(mockObj).add(hotel);
    }

    @Test
    public void getTrueTest() {
        HotelRepositoryImpl.getInstance().add(hotel);
        when(mockObj.get(1)).thenReturn(Optional.of(hotel));
        assertEquals(mockObj.get(1).get(), HotelRepositoryImpl.getInstance().get(1).get());
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        HotelRepositoryImpl.getInstance().add(hotel);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), HotelRepositoryImpl.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        HotelRepositoryImpl.getInstance().add(hotel);
        when(mockObj.remove(hotel)).thenReturn(true);
        assertEquals(mockObj.remove(hotel), HotelRepositoryImpl.getInstance().remove(hotel));
        verify(mockObj).remove(hotel);
    }

    @Test
    public void removeFalseTest() {
        Hotel h = new Hotel();
        HotelRepositoryImpl.getInstance().add(hotel);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), HotelRepositoryImpl.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        HotelRepositoryImpl.getInstance().add(hotel);
        Hotel h = new Hotel();
        h.setId(ID);
        h.setName("new Name");
        h.setCountry(COUNTRY);
        h.setPhone(PHONE);
        h.setStars(STARS);
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), HotelRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        HotelRepositoryImpl.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        HotelRepositoryImpl.getInstance().add(hotel);
        Hotel h = new Hotel();
        h.setId(3);
        h.setName("new Name");
        h.setCountry(COUNTRY);
        h.setPhone(PHONE);
        h.setStars(STARS);
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), HotelRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        HotelRepositoryImpl.getInstance().remove(h);
    }
}
