package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.impl.HotelRepositoryImpl;
import org.junit.Assert;

import java.util.Optional;

public class Test {
    @org.junit.Test
    public void ses() {
        HotelRepository countryRepository = HotelRepositoryImpl.getInstance();
        Hotel hotel = new Hotel();
        hotel.setName("privet");
        countryRepository.add(hotel);
        hotel = new Hotel();
        hotel.setName("kaka");
        hotel.setId(1);
        Optional<Hotel> s = countryRepository.update(hotel);
        Assert.assertTrue(s.isPresent());
    }
}
