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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code ReviewDatabaseRepository} implements {@code ReviewRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
@Transactional
public class ReviewDatabaseRepository implements com.epam.makedon.agency.repository.ReviewRepository {
    private static final Logger LOGGER;
    private static ReviewDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(ReviewDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private static class Mapper implements RowMapper<Review> {
        private static final Mapper INSTANCE = new Mapper();
        private Mapper() {}
        public static Mapper getInstance() { return INSTANCE; }

        private static final String ID = "id";
        private static final String CONTENT = "content";
        private static final String TOUR_ID = "tourId";
        private static final String USER_ID = "userId";

        @Override
        public Review mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(TOUR_ID));

            User user = new User();
            user.setId(rs.getLong(USER_ID));

            Review review = new Review();
            review.setId(rs.getLong(ID));
            review.setContent(rs.getString(CONTENT));
            review.setTour(tour);
            review.setUser(user);

            return review;
        }
    }

    private static final String SQL_INSERT_REVIEW = "INSERT INTO review (fk_tour_id,fk_user_id,review_content) VALUES(:tourId,:userId,:reviewContent)";
    private static final String SQL_SELECT_REVIEW_BY_ID = "SELECT review_id id, review_content content, fk_tour_id tourId, fk_user_id userId FROM review WHERE review_id=:reviewId";
    private static final String SQL_DELETE_REVIEW = "DELETE FROM review WHERE review_id=:reviewId";
    private static final String SQL_UPDATE_REVIEW_BY_ID = "UPDATE review SET fk_tour_id=:tourId,fk_user_id=:userId,review_content=:reviewContent WHERE review_id=:reviewId";

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = false)
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private ReviewDatabaseRepository() {
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

    public static ReviewDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ReviewDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param review object, which be insert into repository
     * @return boolean result
     */
    @Override
    @Transactional
    public boolean add(Review review) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", review.getTour().getId());
        parameters.put("userId", review.getUser().getId());
        parameters.put("reviewContent", review.getContent());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_REVIEW, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    @Transactional
    public Optional<Review> get(long id) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reviewId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_REVIEW_BY_ID, parameters, Mapper.getInstance()));
    }

    /**
     * @param review generic delete method
     * @return boolean result
     */
    @Override
    @Transactional
    public boolean remove(Review review) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reviewId", review.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_REVIEW, parameters);
        return r == 1;
    }

    /**
     * @param review generic update method
     * @return object, wrapped in optional
     */
    @Override
    @Transactional
    public Optional<Review> update(Review review) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", review.getTour().getId());
        parameters.put("userId", review.getUser().getId());
        parameters.put("reviewContent", review.getContent());
        parameters.put("reviewId", review.getId());
        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_REVIEW_BY_ID, parameters);
        if (r == 1) {
            return Optional.of(review);
        }
        return Optional.empty();
    }
}
