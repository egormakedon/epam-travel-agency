package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.EntityType;
import com.epam.makedon.agency.entity.impl.Hotel;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTest.class);

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
        hotel1 = new Hotel(name1, phone1, country1, stars1);
        hotel2 = new Hotel(name2, phone2, country2, stars2);
        hotel3 = new Hotel(name3, phone3, country3, stars3);

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
    public void updateTrueTest() {
        repository.add(hotel1);
        repository.add(hotel2);
        repository.add(hotel3);

        Storage mockCollection = mock(Storage.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.HOTEL));

        long id = hotel1.getId();
        hotel1 = new Hotel("------", phone1, country1, stars1);
        hotel1.setHotelId(id);
        repository.update(hotel1);

        Set<Hotel> set = new HashSet<Hotel>();
        set.add(hotel1);
        set.add(hotel2);
        set.add(hotel3);

        Assert.assertEquals(mockCollection.get(), set);
        LOGGER.info("Update true test execute successfully");
    }
}
