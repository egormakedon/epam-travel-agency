package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private static class UserInfoMapper implements RowMapper<User> {
        private static final UserInfoMapper INSTANCE = new UserInfoMapper();
        private UserInfoMapper() {}
        public static UserInfoMapper getInstance() { return INSTANCE; }

        private static final String ID = "id";
        private static final String LOGIN = "login";
        private static final String PASSWORD = "password";

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getLong(ID));
            user.setLogin(rs.getString(LOGIN));
            user.setPassword(PASSWORD);
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

    private static final class TourButchSetter implements BatchPreparedStatementSetter {
        private static final TourButchSetter INSTANCE = new TourButchSetter();
        private TourButchSetter() {}
        public static TourButchSetter getInstance() { return INSTANCE; }

        private List<Tour> tourList = new ArrayList<>();
        private long userId;
        public void setTourList(List<Tour> tourList) {
            this.tourList = tourList;
        }
        public void setUserId(long userId) {
            this.userId = userId;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            Tour tour = tourList.get(i);
            ps.setLong(1, userId);
            ps.setLong(2, tour.getId());
        }

        @Override
        public int getBatchSize() {
            return tourList.size();
        }
    }

    private static final class ReviewButchSetter implements BatchPreparedStatementSetter {
        private static final ReviewButchSetter INSTANCE = new ReviewButchSetter();
        private ReviewButchSetter() {}
        public static ReviewButchSetter getInstance() { return INSTANCE; }

        private List<Review> reviewList = new ArrayList<>();
        public void setReviewList(List<Review> reviewList) {
            this.reviewList = reviewList;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            Review review = reviewList.get(i);
            ps.setLong(1, review.getId());
            ps.setLong(2, review.getTour().getId());
            ps.setLong(3, review.getUser().getId());
            ps.setString(4, review.getContent());
        }

        @Override
        public int getBatchSize() {
            return reviewList.size();
        }
    }

    private static final String SQL_INSERT_USER_INFO = "INSERT INTO user(user_id,user_login,user_password) VALUES(?,?,crypt(?, gen_salt('md5')))";
    private static final String SQL_INSERT_USER_TOUR_INFO = "INSERT INTO user_tour(fk_user_id,fk_tour_id) VALUES(?,?)";
    private static final String SQL_INSERT_REVIEW_INFO = "INSERT INTO review(review_id,fk_tour_id,fk_user_id,review_content) VALUE(?,?,?,?)";

    private static final String SQL_SELECT_USER_INFO_BY_ID = "SELECT user_id id,user_login login,user_password password FROM user WHERE user_id=?";
    private static final String SQL_SELECT_TOUR_LIST_BY_USER_ID = "SELECT fk_tour_id id FROM user_tour WHERE fk_user_id=?";
    private static final String SQL_SELECT_REVIEW_LIST_BY_USER_ID = "SELECT review_id id FROM review WHERE fk_user_id=?";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id=?";

    private JdbcTemplate jdbcTemplate;

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
    public boolean add(User user) {
        int r = jdbcTemplate.update(SQL_INSERT_USER_INFO, user.getId(), user.getLogin(), user.getPassword());
        if (r !=1 ) {
            return false;
        }


        TourButchSetter.getInstance().setTourList(user.getTourList());
        TourButchSetter.getInstance().setUserId(user.getId());
        int[] res = jdbcTemplate.batchUpdate(SQL_INSERT_USER_TOUR_INFO, TourButchSetter.getInstance());
        for (int index : res) {
            if (index != 1) {
                return false;
            }
        }

        ReviewButchSetter.getInstance().setReviewList(user.getReviewList());
        res = jdbcTemplate.batchUpdate(SQL_INSERT_REVIEW_INFO, ReviewButchSetter.getInstance());
        for (int index : res) {
            if (index != 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<User> get(long id) {
        User user = jdbcTemplate.queryForObject(SQL_SELECT_USER_INFO_BY_ID, UserInfoMapper.getInstance(), id);
        if (user == null) {
            throw new RepositoryException("user doesn't exist");
        }

        List<Tour> tourList = jdbcTemplate.query(SQL_SELECT_TOUR_LIST_BY_USER_ID, TourMapper.getInstance(), id);
        user.setTourList(tourList);

        List<Review> reviewList = jdbcTemplate.query(SQL_SELECT_REVIEW_LIST_BY_USER_ID, ReviewMapper.getInstance(), id);
        user.setReviewList(reviewList);

        return Optional.of(user);
    }

    /**
     * @param user generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(User user) {
        int r = jdbcTemplate.update(SQL_DELETE_USER_BY_ID, user.getId());
        return (r == 1);
    }

    /**
     * @param user generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<User> update(User user) {
        if (remove(user)) {
            if (add(user)) {
                return Optional.of(user);
            } else {
                throw new RepositoryException("user updated wrong");
            }
        }
        return Optional.empty();
    }
}
