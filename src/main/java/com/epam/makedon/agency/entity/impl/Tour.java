package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class {@code Tour} is Entity class.
 * It stores information about tour data.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 1.0
 * @since version 1.0
 */
public class Tour implements Entity {
    private static long id;

    private long tourId;
    private Image photo;
    private LocalDate date;
    private Duration duration;
    private Country country;
    private Hotel hotel;
    private TourType type;
    private String description;
    private int cost;

    public Tour() {
        id = IdCounter.incrementId(id);
        tourId = id;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public long getId() {
        return tourId;
    }

    public Image getPhoto() {
        return photo;
    }

    public LocalDate getDate() {
        return date;
    }

    public Duration getDuration() {
        return duration;
    }

    public Country getCountry() {
        return country;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public TourType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Tour tour = (Tour) o;
        return tourId == tour.tourId &&
                cost == tour.cost &&
                ((photo == tour.getPhoto()) || (photo != null && photo.equals(tour.getPhoto()))) &&
                ((date == tour.getDate()) || (date != null && date.equals(tour.getDate()))) &&
                ((duration == tour.getDuration()) || (duration != null && duration.equals(tour.getDuration()))) &&
                country == tour.getCountry() &&
                type == tour.getType() &&
                ((hotel == tour.getHotel()) || (hotel != null && hotel.equals(tour.getHotel()))) &&
                ((description == tour.getDescription()) || (description != null && description.equals(tour.getDescription())));
    }

    @Override
    public int hashCode() {
        return (int) (31 * tourId + (photo == null ? 0 : photo.hashCode()) +
                (date == null ? 0 : date.hashCode()) + (duration == null ? 0 : duration.hashCode()) +
                (country == null ? 0 : country.hashCode()) + (hotel == null ? 0 : hotel.hashCode()) +
                (type == null ? 0 : type.hashCode()) + (description == null ? 0 : description.hashCode()) + cost);
    }
}
