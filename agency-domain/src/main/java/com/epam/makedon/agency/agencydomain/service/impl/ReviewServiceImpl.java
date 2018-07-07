package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.repository.ReviewRepository;
import com.epam.makedon.agency.agencydomain.service.ReviewService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for {@link Review} class,
 * implements {@link ReviewService} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Service
@Profile("service")

public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * default constructor
     */
    public ReviewServiceImpl() {}

    /**
     * Add operation,
     * supported with {@link Transactional}.
     *
     * @param review {@link Review}
     * @return true/false
     * @throws ServiceException cause param is null
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Review review) {
        if (review == null) {
            LOGGER.error("review argument in add method is null");
            throw new ServiceException("review argument in add method is null");
        }

        return reviewRepository.add(review);
    }

    /**
     * Get/find/take operation,
     * supported with {@link Transactional}.
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public Optional<Review> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        return reviewRepository.get(id);
    }

    /**
     * Remove operation,
     * supported with {@link Transactional}.
     *
     * @param review {@link Review}
     * @return true/false
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Review review) {
        if (review == null) {
            LOGGER.error("review argument in remove method is null");
            throw new ServiceException("review argument in remove method is null");
        }

        return reviewRepository.remove(review);
    }

    /**
     * Update operation,
     * supported with {@link Transactional}.
     *
     * @param review {@link Review}
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Review> update(Review review) {
        if (review == null) {
            LOGGER.error("review argument in update method is null");
            throw new ServiceException("review argument in update method is null");
        }

        return reviewRepository.update(review);
    }
}