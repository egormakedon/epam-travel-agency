package entity;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class Tour implements Entity {
    private static long id;

    private long tourId;
    private Image photo;
    private Date date;
    private Date duration;
    private Country country;
    private Hotel hotel;
    private TourType type;
    private String description;
    private int cost;

    public Tour(Image photo, Date date, Date duration, Country country, Hotel hotel,
                TourType type, String description, int cost) {
        tourId = IdCounter.incrementId(id);

        this.photo = photo;
        this.date = date;
        this.duration = duration;
        this.country = country;
        this.hotel = hotel;
        this.type = type;
        this.description = description;
        this.cost = cost;
    }

    @Override
    public long getId() {
        return tourId;
    }

    public Image getPhoto() {
        return photo;
    }

    public Date getDate() {
        return date;
    }

    public Date getDuration() {
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

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDuration(Date duration) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return tourId == tour.tourId &&
                cost == tour.cost &&
                Objects.equals(photo, tour.photo) &&
                Objects.equals(date, tour.date) &&
                Objects.equals(duration, tour.duration) &&
                country == tour.country &&
                Objects.equals(hotel, tour.hotel) &&
                type == tour.type &&
                Objects.equals(description, tour.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tourId, photo, date, duration, country, hotel, type, description, cost);
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", date=" + date +
                ", duration=" + duration +
                ", country=" + country +
                ", hotel=" + hotel.toString() +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
