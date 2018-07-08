package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Repository class for {@link Review} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainHibernateConfiguration} class,
 * implements {@link ReviewRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("hibernateRepository")

public class ReviewHibernateRepository implements ReviewRepository {

    private static final String REVIEW_ID = "reviewId";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public ReviewHibernateRepository() {}

    /**
     * Add operation
     *
     * @param review {@link Review}
     * @return true/false
     */
    @Override
    public boolean add(Review review) {
        entityManager.merge(review);
        return true;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Review> get(long id) {
        final String NATIVE_QUERY_SELECT_REVIEW_BY_ID = "SELECT * " +
                "FROM review " +
                "WHERE review_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_REVIEW_BY_ID, Review.class);
        query.setParameter(1, id);
        return Optional.ofNullable((Review)query.getSingleResult());
    }

    /**
     * Remove operation
     *
     * @param review {@link Review}
     * @return true/false
     */
    @Override
    public boolean remove(Review review) {
        final String NAMED_QUERY_DELETE_REVIEW_BY_ID = "DELETE " +
                "FROM Review " +
                "WHERE id=:reviewId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_REVIEW_BY_ID);
        query.setParameter(REVIEW_ID, review.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * Update operation
     *
     * @param review {@link Review}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<Review> update(Review review) {
        return Optional.ofNullable(entityManager.merge(review));
    }
}