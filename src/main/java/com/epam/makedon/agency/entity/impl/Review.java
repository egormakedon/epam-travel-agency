package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;

/**
 * Class {@code Review} is Entity class.
 * It store information about users review.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 1.0
 * @since version 1.0
 */
public class Review implements Entity {
    private static long id;

    private long reviewId;
    private Tour tour;
    private User user;
    private String content;

    public Review() {
        id = IdCounter.incrementId(id);
        reviewId = id;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public long getId() {
        return reviewId;
    }

    public Tour getTour() {
        return tour;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Review review = (Review) o;
        return reviewId == review.getId() &&
                tour.equals(review.getTour()) &&
                user.equals(review.getUser()) &&
                content.equals(review.getContent());
    }

    @Override
    public int hashCode() {
        return (int) (31 * reviewId + (tour == null ? 0 : tour.hashCode()) +
                (user == null ? 0 : user.hashCode()) +
                (content == null ? 0 : content.hashCode()));
    }
}
