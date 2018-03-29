package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.repository.RepositoryException;
import com.epam.makedon.agency.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Final singleton class {@code TourRepositoryImpl} implements TourRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 3.0
 */
public class TourRepositoryImpl implements TourRepository {
    private static final Logger LOGGER;
    private static TourRepositoryImpl instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(TourRepositoryImpl.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<Tour> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private TourRepositoryImpl() {
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

    public static TourRepositoryImpl getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new TourRepositoryImpl();
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
}
