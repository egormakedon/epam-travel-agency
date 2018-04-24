package com.epam.makedon.agency.repository.collection;

import com.epam.makedon.agency.entity.impl.Tour;
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
 * Final singleton class {@code TourCollectionRepository} implements TourCollectionRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @deprecated since version 5.0, old tour repository implementation
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 1.0
 */
@Deprecated
@Transactional
public class TourCollectionRepository implements com.epam.makedon.agency.repository.TourRepository {
    private static final Logger LOGGER;
    private static TourCollectionRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(TourCollectionRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<Tour> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private TourCollectionRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }

        set = new HashSet<>();
    }

    public void setSet(Set<Tour> set) {
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

    public static TourCollectionRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new TourCollectionRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param tour object, which be insert into repository
     * @return boolean, the result of adding tour
     */
    @Override
    @Transactional
    public boolean add(Tour tour) {
        LOGGER.info("call tour add method");

        lock.lock();
        try {
            return set.add(tour);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id to define and find object
     * @return tour, wrapped into optional
     */
    @Override
    @Transactional
    public Optional<Tour> get(long id) {
        LOGGER.info("call tour get method");

        lock.lock();
        try {
            Iterator<Tour> iterator = set.iterator();
            Tour tour = null;
            while (iterator.hasNext()) {
                tour = iterator.next();
                if (id == tour.getId()) {
                    break;
                } else {
                    tour = null;
                }
            }
            return Optional.ofNullable(tour);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param tour generic delete method
     * @return boolean, the result of removing tour
     */
    @Override
    @Transactional
    public boolean remove(Tour tour) {
        LOGGER.info("call tour remove method");

        lock.lock();
        try {
            return set.remove(tour);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param tour generic update method
     * @return tour, wrapped into optional
     */
    @Override
    @Transactional
    public Optional<Tour> update(Tour tour) {
        LOGGER.info("call tour update method");

        lock.lock();
        try {
            Iterator<Tour> iterator = set.iterator();
            Tour t = null;
            while(iterator.hasNext()) {
                t = iterator.next();
                if (t.getId() == tour.getId()) {
                    iterator.remove();
                    set.add(tour);
                    t = tour;
                    break;
                } else {
                    t = null;
                }
            }

            return Optional.ofNullable(t);
        } finally {
            lock.unlock();
        }
    }

    private void destroy() {
        set.clear();
    }
}
