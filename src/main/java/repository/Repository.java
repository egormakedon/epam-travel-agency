package repository;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class Repository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);
    private static Repository instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private ICollection<Hotel> hotelCollection = new CollectionImpl<Hotel>();
    private ICollection<User> userCollection = new CollectionImpl<User>();
    private ICollection<Tour> tourCollection = new CollectionImpl<Tour>();
    private ICollection<Review> reviewCollection = new CollectionImpl<Review>();
    private ICollection<Country> countryCollection = new CollectionImpl<Country>();
    private ICollection<TourType> tourTypeCollection = new CollectionImpl<TourType>();

    private Repository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone connection pool with reflection api");
            throw new RuntimeException("Tried to clone connection pool with reflection api");
        }
    }

    public static Repository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new Repository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone connection pool");
            throw new CloneNotSupportedException("Tried to clone connection pool");
        }
        return super.clone();
    }
}