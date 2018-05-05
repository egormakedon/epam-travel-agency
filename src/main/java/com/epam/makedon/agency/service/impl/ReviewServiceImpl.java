package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.repository.ReviewRepository;
import com.epam.makedon.agency.service.ReviewService;
import com.epam.makedon.agency.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class ReviewServiceImpl implements ReviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewServiceImpl() {}

    @Autowired(required = false)
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Autowired(required = false)
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return reviewRepository.add(review);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Review> get(long id) {
        return reviewRepository.get(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return reviewRepository.remove(review);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Review> update(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return reviewRepository.update(review);
    }
}
