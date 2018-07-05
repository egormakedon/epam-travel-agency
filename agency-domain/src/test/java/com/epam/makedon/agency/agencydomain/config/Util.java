package com.epam.makedon.agency.agencydomain.config;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;

/**
 * Utility class for generate objects for testing
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public final class Util {

    private Util() {}

    public static Hotel getHotel() {
        return createHotel();
    }

    private static Hotel createHotel() {
        final String NAME = "hotel4";
        final String PHONE = "2934";

        Hotel hotel = new Hotel();
        hotel.setId(4);
        hotel.setName(NAME);
        hotel.setPhone(PHONE);
        hotel.setStars((byte)4);
        return hotel;
    }
}