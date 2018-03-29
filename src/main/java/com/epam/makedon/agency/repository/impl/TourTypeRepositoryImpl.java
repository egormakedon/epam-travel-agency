package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.TourType;
import com.epam.makedon.agency.repository.RepositoryException;
import com.epam.makedon.agency.repository.TourTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final singleton class {@code TourTypeRepositoryImpl} implements TourTypeRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 3.0
 */
public class TourTypeRepositoryImpl implements TourTypeRepository {
    private static final Logger LOGGER;
    private static TourTypeRepositoryImpl instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(TourTypeRepositoryImpl.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<TourType> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private TourTypeRepositoryImpl() {
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

    public static TourTypeRepositoryImpl getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new TourTypeRepositoryImpl();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param tourType object, which be insert into repository
     * @return boolean, the result of adding tour
     */
    @Override
    public boolean add(TourType tourType) {
        LOGGER.info("call tourType add method");

        lock.lock();
        try {
            return set.add(tourType);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id to define and find object
     * @return tourType, wrapped into optional
     */
    @Override
    public Optional<TourType> get(long id) {
        LOGGER.info("call tourType get method");

        lock.lock();
        try {
            Iterator<TourType> iterator = set.iterator();
            TourType tourType = null;
            while (iterator.hasNext()) {
                tourType = iterator.next();
                if (id == tourType.getId()) {
                    break;
                } else {
                    tourType = null;
                }
            }
            return Optional.ofNullable(tourType);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param tourType generic delete method
     * @return boolean, the result of removing tourType
     */
    @Override
    public boolean remove(TourType tourType) {
        LOGGER.info("call tourType remove method");

        lock.lock();
        try {
            return set.remove(tourType);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param tourType generic update method
     * @return tourType, wrapped into optional
     */
    @Override
    public Optional<TourType> update(TourType tourType) {
        LOGGER.info("call tourType update method");

        lock.lock();
        try {
            Iterator<TourType> iterator = set.iterator();
            TourType t = null;
            while(iterator.hasNext()) {
                t = iterator.next();
                if (t.getId() == tourType.getId()) {
                    iterator.remove();
                    set.add(tourType);
                    t = tourType;
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
