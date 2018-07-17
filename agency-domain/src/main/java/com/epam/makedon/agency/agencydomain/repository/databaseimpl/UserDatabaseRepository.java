package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Role;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
 * Repository class for {@link User} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainDatabaseConfiguration} class,
 * implements {@link UserRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("databaseRepository")

public class UserDatabaseRepository implements UserRepository {

    private static final String USER_LOGIN = "userLogin";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_ID = "userId";
    private static final String ROLE_ID = "roleId";
    private static final String TOUR_ID = "tourId";
    private static final String REVIEW_ID = "reviewId";

    private UserMapper userMapper = new UserMapper();
    private TourMapper tourMapper = new TourMapper();
    private ReviewMapper reviewMapper = new ReviewMapper();

    @Autowired
    private DataSource dataSource;

    /**
     * default constructor
     */
    public UserDatabaseRepository() {}

    /**
     * Add operation
     *
     * @param user {@link User}
     * @return true/false
     */
    @Override
    public boolean add(User user) {
        final String SQL_INSERT_USER = "INSERT INTO user (user_login,user_password) " +
                "VALUES (:userLogin, :userPassword)";
        final String SQL_SELECT_USER_ID_BY_LOGIN = "SELECT user_id userId " +
                "FROM user " +
                "WHERE user_login=:userLogin";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USER_LOGIN, user.getLogin());
        parameters.put(USER_PASSWORD, user.getPassword());

        int r = namedParameterJdbcTemplate.update(SQL_INSERT_USER, parameters);

        if (r != 1) {
            return false;
        }

        if (user.getTourList().isEmpty()) {
            return true;
        }

        parameters.clear();
        parameters.put(USER_LOGIN, user.getLogin());
        long userId = namedParameterJdbcTemplate.queryForObject(SQL_SELECT_USER_ID_BY_LOGIN, parameters, Long.class);
        insertUserTour(userId, user.getTourList());
        return true;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<User> get(long id) {
        final String SQL_SELECT_USER_BY_ID = "SELECT user_id userId, user_login userLogin, " +
                "user_password userPassword, role_id roleId " +
                "FROM user " +
                "WHERE user_id=:userId";
        final String SQL_SELECT_TOUR_USER_ID = "SELECT fk_tour_id tourId " +
                "FROM user_tour " +
                "WHERE fk_user_id=:userId";
        final String SQL_SELECT_REVIEW_BY_USER_ID = "SELECT review_id reviewId " +
                "FROM review " +
                "WHERE fk_user_id=:userId";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USER_ID, id);

        User user = namedParameterJdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, parameters, userMapper);
        List<Tour> tourList = namedParameterJdbcTemplate.query(SQL_SELECT_TOUR_USER_ID, parameters, tourMapper);
        user.setTourList(tourList);
        List<Review> reviewList = namedParameterJdbcTemplate.query(SQL_SELECT_REVIEW_BY_USER_ID, parameters, reviewMapper);
        user.setReviewList(reviewList);

        return Optional.of(user);
    }

    /**
     * Find operation
     *
     * @param username of User
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<User> findByUsername(String username) {
        final String SQL_SELECT_FIND_USER_BY_USERNAME = "SELECT user_id userId " +
                "FROM user " +
                "WHERE user_login=:userLogin";

        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USER_LOGIN, username);
        long id = template.queryForObject(SQL_SELECT_FIND_USER_BY_USERNAME, parameters, Long.class);
        return get(id);
    }

    /**
     * Remove operation
     *
     * @param user {@link User}
     * @return true/false
     */
    @Override
    public boolean remove(User user) {
        final String SQL_DELETE_USER_BY_ID = "DELETE " +
                "FROM user " +
                "WHERE user_id=:userId";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USER_ID, user.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_USER_BY_ID, parameters);
        return r == 1;
    }

    /**
     * Update operation
     *
     * @param user {@link User}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<User> update(User user) {
        final String SQL_UPDATE_USER = "UPDATE user " +
                "SET user_login=:userLogin, user_password=:userPassword, role_id=:roleId " +
                "WHERE user_id=:userId";
        final String SQL_DELETE_USER_TOUR_BY_USER_ID = "DELETE " +
                "FROM user_tour " +
                "WHERE fk_user_id=:userId";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USER_LOGIN, user.getLogin());
        parameters.put(USER_PASSWORD, user.getPassword());
        parameters.put(USER_ID, user.getId());
        parameters.put(ROLE_ID, user.getRole().getId());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_USER, parameters);

        if (r != 1) {
            return Optional.empty();
        }

        parameters.clear();
        parameters.put(USER_ID, user.getId());
        namedParameterJdbcTemplate.update(SQL_DELETE_USER_TOUR_BY_USER_ID, parameters);

        if (user.getTourList().isEmpty()) {
            return Optional.of(user);
        }

        insertUserTour(user.getId(), user.getTourList());
        return Optional.of(user);
    }

    private void insertUserTour(long userId, List<Tour> tourList) {
        final String SQL_INSERT_USER_TOUR = "INSERT INTO user_tour (fk_user_id,fk_tour_id) " +
                "VALUES(:userId, :tourId)";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object>[] batch = new HashMap[tourList.size()];
        for (int index = 0; index < tourList.size(); index++) {
            batch[index] = new HashMap<>();
            batch[index].put(USER_ID, userId);
            batch[index].put(TOUR_ID, tourList.get(index).getId());
        }
        namedParameterJdbcTemplate.batchUpdate(SQL_INSERT_USER_TOUR, batch);
    }

    private class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(USER_LOGIN));
            user.setPassword(rs.getString(USER_PASSWORD));
            user.setRole(Role.fromCode(rs.getLong(ROLE_ID)));
            return user;
        }
    }

    private class TourMapper implements RowMapper<Tour> {

        @Override
        public Tour mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(TOUR_ID));
            return tour;
        }
    }

    private class ReviewMapper implements RowMapper<Review> {

        @Override
        public Review mapRow(ResultSet rs, int i) throws SQLException {
            Review review = new Review();
            review.setId(rs.getLong(REVIEW_ID));
            return review;
        }
    }
}