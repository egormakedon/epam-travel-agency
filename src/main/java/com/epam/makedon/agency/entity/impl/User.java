package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;
import lombok.Data;

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
@Data
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

    @Override
    public long getId() {
        return userId;
    }
}
