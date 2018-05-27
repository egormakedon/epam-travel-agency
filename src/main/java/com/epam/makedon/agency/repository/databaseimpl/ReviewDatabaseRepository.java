package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.domain.impl.Review;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.ReviewRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Class ReviewDatabaseRepository implements ReviewRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 2.0
 */
@Repository
@Profile("databaseRepository")
public class ReviewDatabaseRepository implements ReviewRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDatabaseRepository.class);
    private Mapper mapper = new Mapper();

    @Autowired
    @Setter
    private DataSource dataSource;

    public ReviewDatabaseRepository() {}

    /**
     * @param review object, which be insert into repository
     * @return boolean result of inserting review object
     */
    @Override
    public boolean add(Review review) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_INSERT_REVIEW = "INSERT INTO review (fk_tour_id,fk_user_id,review_content) VALUES(:tourId,:userId,:reviewContent)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", review.getTour().getId());
        parameters.put("userId", review.getUser().getId());
        parameters.put("reviewContent", review.getContent());
        int r = namedParameterJdbcTemplate.update(SQL_INSERT_REVIEW, parameters);
        return r == 1;
    }

    /**
     * @param id to define and find review object
     * @return review object, wrapped in optional
     */
    @Override
    public Optional<Review> get(long id) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String SQL_SELECT_REVIEW_BY_ID = "SELECT review_id id, review_content content, fk_tour_id tourId, fk_user_id userId FROM review WHERE review_id=:reviewId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reviewId", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL_SELECT_REVIEW_BY_ID, parameters, mapper));
    }

    /**
     * @param review object, which be removing from repository
     * @return boolean result of removing review object
     */
    @Override
    public boolean remove(Review review) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_DELETE_REVIEW = "DELETE FROM review WHERE review_id=:reviewId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reviewId", review.getId());
        int r = namedParameterJdbcTemplate.update(SQL_DELETE_REVIEW, parameters);
        return r == 1;
    }

    /**
     * @param review object, which be updating in repository
     * @return review object, wrapped in optional, result of updating
     */
    @Override
    public Optional<Review> update(Review review) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        final String SQL_UPDATE_REVIEW_BY_ID = "UPDATE review SET fk_tour_id=:tourId,fk_user_id=:userId,review_content=:reviewContent WHERE review_id=:reviewId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tourId", review.getTour().getId());
        parameters.put("userId", review.getUser().getId());
        parameters.put("reviewContent", review.getContent());
        parameters.put("reviewId", review.getId());

        int r = namedParameterJdbcTemplate.update(SQL_UPDATE_REVIEW_BY_ID, parameters);

        if (r == 1) {
            return Optional.ofNullable(review);
        } else {
            return Optional.empty();
        }
    }

    private class Mapper implements RowMapper<Review> {
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
}