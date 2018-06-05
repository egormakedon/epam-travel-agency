package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.Review;
import com.epam.makedon.agency.repository.ReviewRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class ReviewHibernateRepository implements ReviewRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class ReviewHibernateRepository implements ReviewRepository {

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
        return false;
    }

    /**
     * @param id to define and find review in repository
     * @return review object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Review> get(long id) {
        return Optional.empty();
    }

    /**
     * @param review object to remove from repository
     * @return boolean result of review removing from repository
     */
    @Override
    public boolean remove(Review review) {
        return false;
    }

    /**
     * @param review object to update in repository
     * @return updated review object, wrapped in optional
     */
    @Override
    public Optional<Review> update(Review review) {
        return Optional.empty();
    }
}
