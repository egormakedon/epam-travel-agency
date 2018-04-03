package com.epam.makedon.agency.repository.collection;

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
    private HotelCollectionRepository mockObj;

    @Before
    public void init() {
        mockObj = mock(HotelCollectionRepository.class);
        hotel = new Hotel();
        hotel.setId(ID);
        hotel.setName(NAME);
        hotel.setCountry(COUNTRY);
        hotel.setPhone(PHONE);
        hotel.setStars(STARS);
        HotelCollectionRepository.getInstance().remove(hotel);
    }

    @After
    public void destroy() {
        mockObj = null;
        HotelCollectionRepository.getInstance().remove(hotel);
        hotel = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(hotel)).thenReturn(true);
        assertEquals(mockObj.add(hotel), HotelCollectionRepository.getInstance().add(hotel));
        verify(mockObj).add(hotel);
    }

    @Test
    public void addFalseTest() {
        HotelCollectionRepository.getInstance().add(hotel);
        when(mockObj.add(hotel)).thenReturn(false);
        assertEquals(mockObj.add(hotel), HotelCollectionRepository.getInstance().add(hotel));
        verify(mockObj).add(hotel);
    }

    @Test
    public void getTrueTest() {
        HotelCollectionRepository.getInstance().add(hotel);
        when(mockObj.get(1)).thenReturn(Optional.of(hotel));
        assertEquals(mockObj.get(1).get(), HotelCollectionRepository.getInstance().get(1).get());
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        HotelCollectionRepository.getInstance().add(hotel);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), HotelCollectionRepository.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        HotelCollectionRepository.getInstance().add(hotel);
        when(mockObj.remove(hotel)).thenReturn(true);
        assertEquals(mockObj.remove(hotel), HotelCollectionRepository.getInstance().remove(hotel));
        verify(mockObj).remove(hotel);
    }

    @Test
    public void removeFalseTest() {
        Hotel h = new Hotel();
        HotelCollectionRepository.getInstance().add(hotel);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), HotelCollectionRepository.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        HotelCollectionRepository.getInstance().add(hotel);
        Hotel h = new Hotel();
        h.setId(ID);
        h.setName("new Name");
        h.setCountry(COUNTRY);
        h.setPhone(PHONE);
        h.setStars(STARS);
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), HotelCollectionRepository.getInstance().update(h));
        verify(mockObj).update(h);
        HotelCollectionRepository.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        HotelCollectionRepository.getInstance().add(hotel);
        Hotel h = new Hotel();
        h.setId(3);
        h.setName("new Name");
        h.setCountry(COUNTRY);
        h.setPhone(PHONE);
        h.setStars(STARS);
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), HotelCollectionRepository.getInstance().update(h));
        verify(mockObj).update(h);
        HotelCollectionRepository.getInstance().remove(h);
    }
}
