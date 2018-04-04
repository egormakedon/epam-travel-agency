package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
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

        private static final String REVIEW_ID = "review_id";
        private static final String REVIEW_CONTENT = "review_content";

        private static final String TOUR_ID = "tour_id";
        private static final String TOUR_PHOTO = "tour_photo";
        private static final String TOUR_DATE = "tour_date";
        private static final String TOUR_DURATION = "tour_duration";
        private static final String TOUR_DESCRIPTION = "tour_description";
        private static final String TOUR_COST = "tour_cost";

        @Override
        public Review mapRow(ResultSet rs, int i) throws SQLException {
            Review review = new Review();
            review.setId(rs.getLong(REVIEW_ID));
            review.setContent(rs.getString(REVIEW_CONTENT));

            //take user

            Tour tour = new Tour();

            //country
            //hotel
            //type
            review.setTour(tour);

            return review;
        }
    }

    private static final String SQL_INSERT_REVIEW = "INSERT INTO review(review_id,fk_tour_id,fk_user_id,review_content) VALUES(?,?,?,?)";
    private static final String SQL_SELECT_REVIEW_BY_ID = "SELECT * FROM review INNER JOIN tour ON review.fk_tour_id=tour.tour_id INNER JOIN user ON review.fk_user_id=user.user_id WHERE review.review_id=?";
    private static final String SQL_DELETE_REVIEW = "DELETE FROM review WHERE review_id=?";

    private JdbcTemplate jdbcTemplate;

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
    public boolean add(Review review) {
        int r = jdbcTemplate.update(SQL_INSERT_REVIEW, review.getId(), review.getTour().getId(), review.getUser().getId(), review.getContent());
        return (r == 1);
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Review> get(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_REVIEW_BY_ID, Mapper.getInstance(), id));
    }

    /**
     * @param review generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Review review) {
        int r = jdbcTemplate.update(SQL_DELETE_REVIEW, review.getId());
        return (r == 1);
    }

    /**
     * @param review generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Review> update(Review review) {
        if (remove(review)) {
            if (add(review)) {
                return Optional.of(review);
            } else {
                throw new RepositoryException("review updated wrong");
            }
        }
        return Optional.empty();
    }
}
