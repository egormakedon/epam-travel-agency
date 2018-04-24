package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code UserDatabaseRepository} implements {@code UserRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
@Transactional
public class UserDatabaseRepository implements com.epam.makedon.agency.repository.UserRepository {
    private static final Logger LOGGER;
    private static UserDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(UserDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private static class UserMapper implements RowMapper<User> {
        private static final UserMapper INSTANCE = new UserMapper();
        private UserMapper() {}
        public static UserMapper getInstance() { return INSTANCE; }

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
    private static class TourMapper implements RowMapper<Tour> {
        private static final TourMapper INSTANCE = new TourMapper();
        private TourMapper() {}
        public static TourMapper getInstance() { return INSTANCE; }

        private static final String ID = "id";

        @Override
        public Tour mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(ID));
            return tour;
        }
    }
    private static final class ReviewMapper implements RowMapper<Review> {
        private static final ReviewMapper INSTANCE = new ReviewMapper();
        private ReviewMapper() {}
        public static ReviewMapper getInstance() { return INSTANCE; }

        private static final String ID = "id";

        @Override
        public Review mapRow(ResultSet rs, int i) throws SQLException {
            Review review = new Review();
            review.setId(rs.getLong(ID));
            return review;
        }
    }

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private UserDatabaseRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * @return Object
     * @throws CloneNotSupportedException when try cloning
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LOGGER.error("Tried to clone singleton");
        throw new CloneNotSupportedException("Tried to clone singleton");
    }

    /**
     * protection from serialization
     *
     * @return Object
     */
    protected Object readResolve() {
        return instance;
    }

    public static UserDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new UserDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param user object, which be insert into repository
     * @return boolean result
     */
    @Override
    @Transactional
    public boolean add(User user) {
        final String SQL_INSERT_USER = "INSERT INTO user (user_login,user_password) VALUES (:userLogin, :userPassword)";
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

        final String SQL_SELECT_USER_ID_BY_LOGIN = "SELECT user_id userId FROM user WHERE user_login=:userLogin";
        parameters.clear();
        parameters.put("userLogin", user.getLogin());
        long userId = namedParameterJdbcTemplate.queryForObject(SQL_SELECT_USER_ID_BY_LOGIN, parameters, Long.class);

        insertUserTour(userId, user.getTourList());

        return true;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    @Transactional
    public Optional<User> get(long id) {
        final String SQL_SELECT_USER_BY_ID = "SELECT user_id id,user_login login,user_password password FROM user WHERE user_id=:userId";
        final String SQL_SELECT_TOUR_USER_ID = "SELECT fk_tour_id id FROM user_tour WHERE fk_user_id=:userId";
        final String SQL_SELECT_REVIEW_BY_USER_ID = "SELECT review_id id FROM review WHERE fk_user_id=:userId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", id);

        User user = namedParameterJdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, parameters, UserMapper.getInstance());
        if (user == null) {
            return Optional.empty();
        }

        List<Tour> tourList = namedParameterJdbcTemplate.query(SQL_SELECT_TOUR_USER_ID, parameters, TourMapper.getInstance());
        user.setTourList(tourList);

        List<Review> reviewList = namedParameterJdbcTemplate.query(SQL_SELECT_REVIEW_BY_USER_ID, parameters, ReviewMapper.getInstance());
        user.setReviewList(reviewList);

        return Optional.of(user);
    }

    /**
     * @param user generic delete method
     * @return boolean result
     */
    @Override
    @Transactional
    public boolean remove(User user) {
        final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id=:userId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", user.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_USER_BY_ID, parameters);
        return r == 1;
    }

    /**
     * @param user generic update method
     * @return object, wrapped in optional
     */
    @Override
    @Transactional
    public Optional<User> update(User user) {
        final String SQL_UPDATE_USER = "UPDATE user SET user_login=:userLogin, user_password=:userPassword WHERE user_id=:userId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userLogin", user.getLogin());
        parameters.put("userPassword", user.getPassword());
        parameters.put("userId", user.getId());
        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_USER, parameters);
        if (r != 1) {
            return Optional.empty();
        }

        final String SQL_DELETE_USER_TOUR_BY_USER_ID = "DELETE FROM user_tour WHERE fk_user_id=:userId";
        parameters.clear();
        parameters.put("userId", user.getId());
        namedParameterJdbcTemplate.update(SQL_DELETE_USER_TOUR_BY_USER_ID, parameters);

        if (user.getTourList().isEmpty()) {
            return Optional.of(user);
        }

        insertUserTour(user.getId(), user.getTourList());

        return Optional.of(user);
    }

    private void insertUserTour(long userId, List<Tour> tourList) {
        final String SQL_INSERT_USER_TOUR = "INSERT INTO user_tour (fk_user_id,fk_tour_id) VALUES(:userId,:tourId)";
        Map<String, Object>[] batch = new HashMap[tourList.size()];
        for (int index = 0; index < tourList.size(); index++) {
            batch[index].put("userId", userId);
            batch[index].put("tourId", tourList.get(index).getId());
        }
        namedParameterJdbcTemplate.batchUpdate(SQL_INSERT_USER_TOUR, batch);
    }
}
