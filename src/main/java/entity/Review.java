package entity;

import java.util.Objects;

public class Review {
    private static long id;

    private long reviewId;
    private Tour tour;
    private User user;
    private String content;

    public Review(Tour tour, User user, String content) {
        reviewId = IdCounter.incrementId(id);

        this.tour = tour;
        this.user = user;
        this.content = content;
    }

    public long getReviewId() {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId &&
                Objects.equals(tour, review.tour) &&
                Objects.equals(user, review.user) &&
                Objects.equals(content, review.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewId, tour, user, content);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", tour=" + tour +
                ", user=" + user +
                ", content='" + content + '\'' +
                '}';
    }
}
