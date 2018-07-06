package com.epam.makedon.agency.agencydomain.config;

import com.epam.makedon.agency.agencydomain.domain.impl.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

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

    public static Review getReview() {
        return createReview();
    }

    public static Tour getTour() {
        return createTour();
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
        User user = new User();
        user.setId(1);

        Tour tour = new Tour();
        tour.setId(4);

        Hotel hotel = new Hotel();
        hotel.setId(1);

        Review review = new Review();
        review.setId(5);
        review.setUser(user);
        review.setTour(tour);
        review.setContent("the worst tour ever");
        return review;
    }

    private static Tour createTour() {
        Tour tour = new Tour();
        tour.setType(TourType.WEEKEND);
        tour.setCountry(Country.SPAIN);
        tour.setId(3);
        Hotel hotel = new Hotel();
        hotel.setId(1);
        tour.setHotel(hotel);
        tour.setDuration(Duration.ofDays(2));
        tour.setDescription("123");
        tour.setDate(LocalDate.now());
        tour.setCost(BigDecimal.valueOf(1));
        return tour;
    }
}