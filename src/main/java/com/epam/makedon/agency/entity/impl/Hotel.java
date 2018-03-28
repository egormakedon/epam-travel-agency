package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;

/**
 * Class {@code Hotel} is Entity class.
 * This class stores information about hotels, which uses in project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 3.0
 * @since version 1.0
 */
public class Hotel implements Entity {
    private static Long id = 0L;

    private long hotelId;
    private String name;
    private String phone;
    private Country country;
    private byte stars;

    public Hotel() {
        hotelId = IdCounter.incrementId(id);
    }

    @Override
    public void setId(long hotelId) {
        this.hotelId = hotelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setStars(byte stars) {
        this.stars = stars;
    }

    @Override
    public long getId() {
        return hotelId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Country getCountry() {
        return country;
    }

    public byte getStars() {
        return stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Hotel hotel = (Hotel) o;
        return hotelId == hotel.getId() &&
                stars == hotel.getStars() &&
                (name != null && name.equals(hotel.getName())) &&
                (phone != null && phone.equals(hotel.getPhone())) &&
                (country != null && country.equals(hotel.getCountry()));
    }

    @Override
    public int hashCode() {
        return (int) (31 * hotelId + (name == null ? 0 : name.hashCode()) +
                (phone == null ? 0 : phone.hashCode()) + (int) stars +
                (country == null ? 0 : country.ordinal()));
    }
}