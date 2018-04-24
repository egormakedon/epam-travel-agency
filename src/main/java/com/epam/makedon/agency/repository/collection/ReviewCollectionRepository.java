package com.epam.makedon.agency.repository.collection;

import com.epam.makedon.agency.entity.impl.Review;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final singleton class {@code ReviewCollectionRepository} implements ReviewCollectionRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @deprecated since version 5.0, old review repository implementation
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 1.0
 */
@Deprecated
@Transactional
public class ReviewCollectionRepository implements com.epam.makedon.agency.repository.ReviewRepository {
    private static final Logger LOGGER;
    private static ReviewCollectionRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(ReviewCollectionRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<Review> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private ReviewCollectionRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }

        set = new HashSet<>();
    }

    public void setSet(Set<Review> set) {
        this.set = set;
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

    public static ReviewCollectionRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ReviewCollectionRepository();
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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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

    private void destroy() {
        set.clear();
    }
}
