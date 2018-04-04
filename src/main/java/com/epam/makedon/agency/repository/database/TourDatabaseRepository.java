package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code TourDatabaseRepository} implements {@code TourRepository} interface.
 * This class realize methods for connecting with database.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 4.0
 */
public class TourDatabaseRepository implements com.epam.makedon.agency.repository.TourRepository {
    private static final Logger LOGGER;
    private static TourDatabaseRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(TourDatabaseRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private JdbcTemplate jdbcTemplate;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private TourDatabaseRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public static TourDatabaseRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new TourDatabaseRepository();
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
     * @return boolean result
     */
    @Override
    public boolean add(Tour tour) {
        return false;
    }

    /**
     * @param id to define and find object
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> get(long id) {
        return Optional.empty();
    }

    /**
     * @param tour generic delete method
     * @return boolean result
     */
    @Override
    public boolean remove(Tour tour) {
        return false;
    }

    /**
     * @param tour generic update method
     * @return object, wrapped in optional
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        return Optional.empty();
    }
}
