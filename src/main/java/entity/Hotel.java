package entity;

import java.util.Objects;

public class Hotel implements Entity {
    private static long id;

    private long hotelId;
    private String name;
    private String phone;
    private Country country;
    private byte stars;

    public Hotel(String name, String phone, Country country, byte stars) {
        hotelId = IdCounter.incrementId(id);

        this.name = name;
        this.phone = phone;
        this.country = country;
        this.stars = stars;
    }

    public long getHotelId() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return hotelId == hotel.hotelId &&
                stars == hotel.stars &&
                Objects.equals(name, hotel.name) &&
                Objects.equals(phone, hotel.phone) &&
                country == hotel.country;
    }

    @Override
    public int hashCode() {

        return Objects.hash(hotelId, name, phone, country, stars);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", country=" + country +
                ", stars=" + stars +
                '}';
    }
}
