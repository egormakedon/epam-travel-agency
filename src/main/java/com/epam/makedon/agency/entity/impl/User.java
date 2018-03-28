package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code User} is Entity class.
 * It stores user information.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 3.0
 * @since version 1.0
 */
public class User implements Entity {
    private static Long id = 0L;

    private long userId;
    private String login;
    private String password;

    private List<Tour> tourList;
    private List<Review> reviewList;

    public User() {
        userId = IdCounter.incrementId(id);
        tourList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    @Override
    public void setId(long userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTourList(Tour tour) {
        tourList.add(tour);
    }

    public void addReviewList(Review review) {
        reviewList.add(review);
    }

    @Override
    public long getId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Tour> getTourList() {
        return tourList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return userId == user.getId() &&
                (login != null && login.equals(user.getLogin())) &&
                (password != null && password.equals(user.getPassword()));
    }

    @Override
    public int hashCode() {
        return (int) (31 * userId + (login == null ? 0 : login.hashCode()) +
                (password == null ? 0 : password.hashCode()));
    }
}
