package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.domain.impl.Review;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class UserDatabaseRepository implements UserRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 2.0
 */
@Repository
public class UserDatabaseRepository implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDatabaseRepository.class);

    private UserMapper userMapper = new UserMapper();
    private TourMapper tourMapper = new TourMapper();
    private ReviewMapper reviewMapper = new ReviewMapper();

    @Autowired
    @Setter
    private DataSource dataSource;

    public UserDatabaseRepository() {}

    /**
     * @param user object, which be inserting into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(User user) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_USER = "INSERT INTO user (user_login,user_password) VALUES (:userLogin, :userPassword)";
        final String SQL_SELECT_USER_ID_BY_LOGIN = "SELECT user_id userId FROM user WHERE user_login=:userLogin";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userLogin", user.getLogin());
        parameters.put("userPassword", user.getPassword());

        int r = namedParameterJdbcTemplate.update(SQL_INSERT_USER, parameters);

        if (r != 1) {
            return false;
        }

        if (user.getTourList().isEmpty()) {
            return true;
        }


        parameters.clear();
        parameters.put("userLogin", user.getLogin());
        long userId = namedParameterJdbcTemplate.queryForObject(SQL_SELECT_USER_ID_BY_LOGIN, parameters, Long.class);
        insertUserTour(userId, user.getTourList());
        return true;
    }

    /**
     * @param id to define and find user object
     * @return user object, wrapped in optional
     */
    @Override
    public Optional<User> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_USER_BY_ID = "SELECT user_id id,user_login login,user_password password FROM user WHERE user_id=:userId";
        final String SQL_SELECT_TOUR_USER_ID = "SELECT fk_tour_id id FROM user_tour WHERE fk_user_id=:userId";
        final String SQL_SELECT_REVIEW_BY_USER_ID = "SELECT review_id id FROM review WHERE fk_user_id=:userId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", id);

        User user = namedParameterJdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, parameters, userMapper);
        if (user == null) {
            return Optional.empty();
        }

        List<Tour> tourList = namedParameterJdbcTemplate.query(SQL_SELECT_TOUR_USER_ID, parameters, tourMapper);
        user.setTourList(tourList);

        List<Review> reviewList = namedParameterJdbcTemplate.query(SQL_SELECT_REVIEW_BY_USER_ID, parameters, reviewMapper);
        user.setReviewList(reviewList);

        return Optional.ofNullable(user);
    }

    /**
     * @param user object, which be removing from repository
     * @return boolean result of removing
     */
    @Override
    public boolean remove(User user) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id=:userId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", user.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_USER_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param user object, which be updating in repository
     * @return user object, wrapped in optional
     */
    @Override
    public Optional<User> update(User user) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        final String SQL_UPDATE_USER = "UPDATE user SET user_login=:userLogin, user_password=:userPassword WHERE user_id=:userId";
        final String SQL_DELETE_USER_TOUR_BY_USER_ID = "DELETE FROM user_tour WHERE fk_user_id=:userId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userLogin", user.getLogin());
        parameters.put("userPassword", user.getPassword());
        parameters.put("userId", user.getId());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_USER, parameters);

        if (r != 1) {
            return Optional.empty();
        }

        parameters.clear();
        parameters.put("userId", user.getId());
        namedParameterJdbcTemplate.update(SQL_DELETE_USER_TOUR_BY_USER_ID, parameters);

        if (user.getTourList().isEmpty()) {
            return Optional.ofNullable(user);
        }

        insertUserTour(user.getId(), user.getTourList());
        return Optional.ofNullable(user);
    }

    private void insertUserTour(long userId, List<Tour> tourList) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_USER_TOUR = "INSERT INTO user_tour (fk_user_id,fk_tour_id) VALUES(:userId,:tourId)";

        Map<String, Object>[] batch = new HashMap[tourList.size()];
        for (int index = 0; index < tourList.size(); index++) {
            batch[index].put("userId", userId);
            batch[index].put("tourId", tourList.get(index).getId());
        }

        namedParameterJdbcTemplate.batchUpdate(SQL_INSERT_USER_TOUR, batch);
    }

    private class UserMapper implements RowMapper<User> {
        private static final String ID = "id";
        private static final String LOGIN = "login";
        private static final String PASSWORD = "password";

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getLong(ID));
            user.setLogin(rs.getString(LOGIN));
            user.setPassword(rs.getString(PASSWORD));
            return user;
        }
    }
    private class TourMapper implements RowMapper<Tour> {
        private static final String ID = "id";

        @Override
        public Tour mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(ID));
            return tour;
        }
    }
    private class ReviewMapper implements RowMapper<Review> {
        private static final String ID = "id";

        @Override
        public Review mapRow(ResultSet rs, int i) throws SQLException {
            Review review = new Review();
            review.setId(rs.getLong(ID));
            return review;
        }
    }
}