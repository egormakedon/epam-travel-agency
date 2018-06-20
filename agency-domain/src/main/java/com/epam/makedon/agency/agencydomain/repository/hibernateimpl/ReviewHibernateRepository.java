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
 * Class ReviewHibernateRepository implements ReviewRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class ReviewHibernateRepository implements ReviewRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public ReviewHibernateRepository() {}

    /**
     * @param review object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Review review) {
        entityManager.merge(review);
        return true;
    }

    /**
     * @param id to define and find review in repository
     * @return review object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Review> get(long id) {
        final String NATIVE_QUERY_SELECT_REVIEW_BY_ID = "SELECT * FROM review WHERE review_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_REVIEW_BY_ID, Review.class);
        query.setParameter(1, id);
        return Optional.ofNullable((Review)query.getSingleResult());
    }

    /**
     * @param review object to remove from repository
     * @return boolean result of review removing from repository
     */
    @Override
    public boolean remove(Review review) {
        final String NAMED_QUERY_DELETE_REVIEW_BY_ID = "DELETE FROM Review WHERE id=:reviewId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_REVIEW_BY_ID);
        query.setParameter("reviewId", review.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * @param review object to update in repository
     * @return updated review object, wrapped in optional
     */
    @Override
    public Optional<Review> update(Review review) {
        return Optional.ofNullable(entityManager.merge(review));
    }
}
