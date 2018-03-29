package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.repository.impl.ReviewRepositoryImpl;
import com.epam.makedon.agency.service.ReviewService;
import com.epam.makedon.agency.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public class ReviewServiceImpl implements ReviewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Override
    public boolean add(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return ReviewRepositoryImpl.getInstance().add(review);
    }

    @Override
    public Optional<Review> get(long id) {
        return ReviewRepositoryImpl.getInstance().get(id);
    }

    @Override
    public boolean remove(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return ReviewRepositoryImpl.getInstance().remove(review);
    }

    @Override
    public Optional<Review> update(Review review) {
        if (review == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return ReviewRepositoryImpl.getInstance().update(review);
    }
}
