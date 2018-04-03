package com.epam.makedon.agency.service;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class ReviewServiceImpl implements ReviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public boolean add(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return reviewRepository.add(review);
    }

    @Override
    public Optional<Review> get(long id) {
        return reviewRepository.get(id);
    }

    @Override
    public boolean remove(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return reviewRepository.remove(review);
    }

    @Override
    public Optional<Review> update(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return reviewRepository.update(review);
    }
}
