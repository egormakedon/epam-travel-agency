package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.*;
import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.entity.impl.Tour;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryGetTest {
    private static final String name1 = "Agency1";
    private static final String phone1 = "12345";
    private static final Country country1 = Country.BELARUS;
    private static final byte stars1 = 5;

    private static final String name2 = "Agency2";
    private static final String phone2 = "1234567";
    private static final Country country2 = Country.RUSSIA;
    private static final byte stars2 = 1;

    private static final String name3 = "Agency3";
    private static final String phone3 = "1234511";
    private static final Country country3 = Country.ENGLAND;
    private static final byte stars3 = 10;

    private Hotel hotel1;
    private Hotel hotel2;
    private Hotel hotel3;

    private Repository repository;

    @Before
    public void init() {
        hotel1 = new Hotel();
        hotel1.setName(name1);
        hotel1.setPhone(phone1);
        hotel1.setCountry(country1);
        hotel1.setStars(stars1);

        hotel2 = new Hotel();
        hotel2.setName(name2);
        hotel2.setPhone(phone2);
        hotel2.setCountry(country2);
        hotel2.setStars(stars2);

        hotel3 = new Hotel();
        hotel3.setName(name3);
        hotel3.setPhone(phone3);
        hotel3.setCountry(country3);
        hotel3.setStars(stars3);

        repository = Repository.getInstance();
    }

    @After
    public void destr() {
        repository.get(EntityType.HOTEL).clear();

        hotel1 = null;
        hotel2 = null;
        hotel3 = null;

        repository = null;
    }

    @Test
    public void getTrueTest1() {
        Storage mockCollection = mock(Storage.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.USER));

        Assert.assertEquals(mockCollection.get().size(), 0);
    }

    @Test
    public void getTrueTest2() {
        repository.add(hotel1);
        repository.add(hotel2);
        repository.add(hotel3);

        Storage mockCollection = mock(Storage.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.HOTEL));

        Assert.assertEquals(mockCollection.get().size(), 3);
    }

    @Test
    public void getFalseTest() {
        repository.add(hotel1);
        repository.add(hotel2);
        repository.add(hotel3);

        Storage mockCollection = mock(Storage.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.HOTEL));

        Assert.assertNotEquals(mockCollection.get().size(), 2);
    }

    @Test
    public void getDefiniteEntityNotNullTest() {
        repository.add(hotel1);

        Optional<Entity> optional = repository.get(hotel1.getId(), EntityType.HOTEL);
        Assert.assertTrue(optional.isPresent());
    }

    @Test
    public void getDefiniteEntityNullTest() {
        Optional<Entity> optional = repository.get(hotel1.getId(), EntityType.HOTEL);
        Assert.assertFalse(optional.isPresent());
    }
}
