package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code HotelDatabaseRepository} implements {@code HotelRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
public class HotelDatabaseRepository implements com.epam.makedon.agency.repository.HotelRepository {
    private static final Logger LOGGER;
    private static HotelDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(HotelDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private HotelDatabaseRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }
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

    public static HotelDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new HotelDatabaseRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param entity object, which be insert into repository
     * @return boolean result
     */
    @Override
    public boolean add(Hotel entity) {
        return false;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Hotel> get(long id) {
        return Optional.empty();
    }

    /**
     * @param entity generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Hotel entity) {
        return false;
    }

    /**
     * @param entity generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Hotel> update(Hotel entity) {
        return Optional.empty();
    }
}
