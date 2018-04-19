package com.epam.makedon.agency.repository.collection;

import com.epam.makedon.agency.entity.impl.Country;
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
 * Final singleton class {@code CountryCollectionRepository} implements CountryCollectionRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @deprecated since version 5.0, old country repository implementation, {@see CountryDatabaseRepository}
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 1.0
 */
@Deprecated
public class CountryCollectionRepository implements com.epam.makedon.agency.repository.CountryRepository {
    private static final Logger LOGGER;
    private static CountryCollectionRepository instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    private Set<Country> set;

    static {
        LOGGER = LoggerFactory.getLogger(CountryCollectionRepository.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private CountryCollectionRepository() {
        if (instanceCreated.get()) {
            LOGGER.error("Tried to clone singleton with reflection api");
            throw new RepositoryException("Tried to clone singleton with reflection api");
        }

        set = new HashSet<>();
    }

    public void setSet(Set<Country> set) {
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

    public static CountryCollectionRepository getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new CountryCollectionRepository();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param country object, which be insert into repository
     * @return boolean, the result of adding country
     */
    @Override
    public boolean add(Country country) {
        LOGGER.info("call country add method");

        lock.lock();
        try {
            return set.add(country);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id to define and find object
     * @return country, wrapped into optional
     */
    @Override
    public Optional<Country> get(long id) {
        LOGGER.info("call country get method");

        lock.lock();
        try {
            Iterator<Country> iterator = set.iterator();
            Country country = null;
            while (iterator.hasNext()) {
                country = iterator.next();
                if (id == country.getId()) {
                    break;
                } else {
                    country = null;
                }
            }
            return Optional.ofNullable(country);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param country generic delete method
     * @return boolean, the result of removing country
     */
    @Override
    public boolean remove(Country country) {
        LOGGER.info("call country remove method");

        lock.lock();
        try {
            return set.remove(country);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param country generic update method
     * @return county, wrapped into optional
     */
    @Override
    public Optional<Country> update(Country country) {
        LOGGER.info("call country update method");

        lock.lock();
        try {
            Iterator<Country> iterator = set.iterator();
            Country c = null;
            while(iterator.hasNext()) {
                c = iterator.next();
                if (c.getId() == country.getId()) {
                    iterator.remove();
                    set.add(country);
                    c = country;
                    break;
                } else {
                    c = null;
                }
            }

            return Optional.ofNullable(c);
        } finally {
            lock.unlock();
        }
    }

    private void destroy() {
        set.clear();
    }
}
