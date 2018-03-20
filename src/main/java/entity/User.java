package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class {@code User} is Entity class.
 * It stores user information.
 *
 * @author Yahor Makedon
 * @see entity
 * @version 1.0
 * @since version 1.0
 */
public class User implements Entity {
    private static long id;

    private long userId;
    private String login;
    private String password;
    private List<Tour> tourList;
    private List<Review> reviewList;

    public User(String login, String password) {
        userId = IdCounter.incrementId(id);

        this.login = login;
        this.password = password;

        tourList = new ArrayList<Tour>();
        reviewList = new ArrayList<Review>();
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTour(Tour tour) {
        tourList.add(tour);
    }

    public void addReview(Review review) {
        reviewList.add(review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(tourList, user.tourList) &&
                Objects.equals(reviewList, user.reviewList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, login, password, tourList, reviewList);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
