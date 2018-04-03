package com.epam.makedon.agency.repository.collection;

import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Final singleton class {@code HotelCollectionRepository} implements HotelCollectionRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 3.0
 */
public class HotelCollectionRepository implements com.epam.makedon.agency.repository.HotelRepository {
    private static final Logger LOGGER;
    private static HotelCollectionRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(HotelCollectionRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<Hotel> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private HotelCollectionRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }

        set = new HashSet<>();
    }

    public void setSet(Set<Hotel> set) {
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

    public static HotelCollectionRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new HotelCollectionRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param hotel object, which be insert into repository
     * @return boolean, the result of adding country
     */
    @Override
    public boolean add(Hotel hotel) {
        LOGGER.info("call hotel add method");

        lock.lock();
        try {
            return set.add(hotel);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id to define and find object
     * @return hotel, wrapped into optional
     */
    @Override
    public Optional<Hotel> get(long id) {
        LOGGER.info("call hotel get method");

        lock.lock();
        try {
            Iterator<Hotel> iterator = set.iterator();
            Hotel hotel = null;
            while (iterator.hasNext()) {
                hotel = iterator.next();
                if (id == hotel.getId()) {
                    break;
                } else {
                    hotel = null;
                }
            }
            return Optional.ofNullable(hotel);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param hotel generic delete method
     * @return boolean, the result of removing hotel
     */
    @Override
    public boolean remove(Hotel hotel) {
        LOGGER.info("call hotel remove method");

        lock.lock();
        try {
            return set.remove(hotel);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param hotel generic update method
     * @return hotel, wrapped into optional
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        LOGGER.info("call hotel update method");

        lock.lock();
        try {
            Iterator<Hotel> iterator = set.iterator();
            Hotel h = null;
            while(iterator.hasNext()) {
                h = iterator.next();
                if (h.getId() == hotel.getId()) {
                    iterator.remove();
                    set.add(hotel);
                    h = hotel;
                    break;
                } else {
                    h = null;
                }
            }

            return Optional.ofNullable(h);
        } finally {
            lock.unlock();
        }
    }

    private void destroy() {
        set.clear();
    }
}
