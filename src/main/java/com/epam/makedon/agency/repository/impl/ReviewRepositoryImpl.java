package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.repository.RepositoryException;
import com.epam.makedon.agency.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final singleton class {@code ReviewRepositoryImpl} implements ReviewRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 3.0
 */
public final class ReviewRepositoryImpl implements ReviewRepository {
    private static final Logger LOGGER;
    private static ReviewRepositoryImpl instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(ReviewRepositoryImpl.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<Review> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private ReviewRepositoryImpl() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }

        set = new HashSet<>();
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

    public static ReviewRepositoryImpl getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ReviewRepositoryImpl();
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
     * @return boolean, the result of adding review
     */
    @Override
    public boolean add(Review review) {
        LOGGER.info("call review add method");

        lock.lock();
        try {
            return set.add(review);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id to define and find object
     * @return review, wrapped into optional
     */
    @Override
    public Optional<Review> get(long id) {
        LOGGER.info("call review get method");

        lock.lock();
        try {
            Iterator<Review> iterator = set.iterator();
            Review review = null;
            while (iterator.hasNext()) {
                review = iterator.next();
                if (id == review.getId()) {
                    break;
                } else {
                    review = null;
                }
            }
            return Optional.ofNullable(review);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param review generic delete method
     * @return boolean, the result of removing review
     */
    @Override
    public boolean remove(Review review) {
        LOGGER.info("call review remove method");

        lock.lock();
        try {
            return set.remove(review);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param review generic update method
     * @return review, wrapped into optional
     */
    @Override
    public Optional<Review> update(Review review) {
        LOGGER.info("call review update method");

        lock.lock();
        try {
            Iterator<Review> iterator = set.iterator();
            Review r = null;
            while(iterator.hasNext()) {
                r = iterator.next();
                if (r.getId() == review.getId()) {
                    iterator.remove();
                    set.add(review);
                    r = review;
                    break;
                } else {
                    r = null;
                }
            }

            return Optional.ofNullable(r);
        } finally {
            lock.unlock();
        }
    }
}
