package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class for {@link Review} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainDatabaseConfiguration} class,
 * implements {@link ReviewRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("databaseRepository")

public class ReviewDatabaseRepository implements ReviewRepository {

    private static final String REVIEW_ID = "reviewId";
    private static final String REVIEW_CONTENT = "reviewContent";
    private static final String USER_ID = "userId";
    private static final String TOUR_ID = "tourId";

    @Autowired
    private DataSource dataSource;
    private Mapper mapper = new Mapper();

    /**
     * default constructor
     */
    public ReviewDatabaseRepository() {}

    /**
     * Add operation
     *
     * @param review {@link Review}
     * @return true/false
     */
    @Override
    public boolean add(Review review) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_REVIEW = "INSERT " +
                "INTO review (fk_tour_id,fk_user_id,review_content) " +
                "VALUES(:tourId,:userId,:reviewContent)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TOUR_ID, review.getTour().getId());
        parameters.put(USER_ID, review.getUser().getId());
        parameters.put(REVIEW_CONTENT, review.getContent());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_REVIEW, parameters);
        return r == 1;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Review> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_SELECT_REVIEW_BY_ID = "SELECT review_id reviewId, review_content reviewContent, " +
                "fk_tour_id tourId, fk_user_id userId " +
                "FROM review " +
                "WHERE review_id=:reviewId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(REVIEW_ID, id);
        return Optional.of(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_REVIEW_BY_ID, parameters, mapper));
    }

    /**
     * Remove operation
     *
     * @param review {@link Review}
     * @return true/false
     */
    @Override
    public boolean remove(Review review) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_REVIEW = "DELETE " +
                "FROM review " +
                "WHERE review_id=:reviewId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(REVIEW_ID, review.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_REVIEW, parameters);
        return r == 1;
    }

    /**
     * Update operation
     *
     * @param review {@link Review}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Review> update(Review review) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_REVIEW_BY_ID = "UPDATE review " +
                "SET fk_tour_id=:tourId,fk_user_id=:userId,review_content=:reviewContent " +
                "WHERE review_id=:reviewId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TOUR_ID, review.getTour().getId());
        parameters.put(USER_ID, review.getUser().getId());
        parameters.put(REVIEW_CONTENT, review.getContent());
        parameters.put(REVIEW_ID, review.getId());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_REVIEW_BY_ID, parameters);

        if (r == 1) {
            return Optional.of(review);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Review> {

        @Override
        public Review mapRow(ResultSet rs, int i) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getLong(TOUR_ID));

            User user = new User();
            user.setId(rs.getLong(USER_ID));

            Review review = new Review();
            review.setId(rs.getLong(REVIEW_ID));
            review.setContent(rs.getString(REVIEW_CONTENT));
            review.setTour(tour);
            review.setUser(user);

            return review;
        }
    }
}