package com.epam.makedon.agency.agencydomain.config;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;

/**
 * Utility class for generate objects for testing
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public final class Util {
    public static Hotel getHotel() {
        return createHotel();
    }

    public static Review getReview() {
        return createReview();
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

    private static Review createReview() {
        Review review = new Review();
        review.setId(5);
        User user = new User();
        user.setId(1);
        Tour tour = new Tour();
        tour.setId(4);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("the worst tour ever");
        return review;
    }
}