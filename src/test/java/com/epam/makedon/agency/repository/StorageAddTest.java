package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.entity.impl.Hotel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StorageAddTest {
    private static final String name1 = "Agency1";
    private static final String phone1 = "12345";
    private static final Country country1 = Country.BELARUS;
    private static final byte stars1 = 5;

    private Hotel hotel1;
    private Storage<Hotel> storage;

    @Before
    public void init() {
        hotel1 = new Hotel();
        hotel1.setName(name1);
        hotel1.setPhone(phone1);
        hotel1.setCountry(country1);
        hotel1.setStars(stars1);

        storage = new Storage<Hotel>();
    }

    @After
    public void destr() {
        hotel1 = null;

        storage.get().clear();
        storage = null;
    }

    @Test
    public void addTrueTest() {
        Storage<Hotel> storage = new Storage<Hotel>();
        storage.add(hotel1);
        Assert.assertEquals(hotel1, storage.get(hotel1.getId()));
    }

    @Test
    public void addFalseTest() {
        Storage<Hotel> storage = new Storage<Hotel>();
        storage.add(hotel1);
        long id = hotel1.getId();
        hotel1 = new Hotel();
        Assert.assertNotEquals(hotel1, storage.get(id));
    }
}
