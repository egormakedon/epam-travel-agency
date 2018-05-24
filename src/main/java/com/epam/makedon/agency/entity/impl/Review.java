package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;
import lombok.Data;

/**
 * Class {@code Review} is Entity class.
 * It store information about users review.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 3.0
 * @since version 1.0
 */
@Data
public class Review implements Entity {
    private static Long id = 0L;

    private long reviewId;
    private Tour tour;
    private User user;
    private String content;

    @Override
    public void setId(long reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public long getId() {
        return reviewId;
    }
}