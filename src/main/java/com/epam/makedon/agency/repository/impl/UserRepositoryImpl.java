package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.RepositoryException;
import com.epam.makedon.agency.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final singleton class {@code UserRepositoryImpl} implements UserRepository interface.
 * Is thead-safe and protected from any cloning
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 3.0
 */
public final class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER;
    private static UserRepositoryImpl instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock;

    static {
        LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
        instanceCreated = new AtomicBoolean(false);
        lock = new ReentrantLock();
    }

    private Set<User> set;

    /**
     * @throws RepositoryException when try cloning with reflection-api
     */
    private UserRepositoryImpl() {
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

    public static UserRepositoryImpl getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new UserRepositoryImpl();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * @param user object, which be insert into repository
     * @return boolean, the result of adding user
     */
    @Override
    public boolean add(User user) {
        LOGGER.info("call user add method");

        lock.lock();
        try {
            return set.add(user);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id to define and find object
     * @return user, wrapped into optional
     */
    @Override
    public Optional<User> get(long id) {
        LOGGER.info("call user get method");

        lock.lock();
        try {
            Iterator<User> iterator = set.iterator();
            User user = null;
            while (iterator.hasNext()) {
                user = iterator.next();
                if (id == user.getId()) {
                    break;
                } else {
                    user = null;
                }
            }
            return Optional.ofNullable(user);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param user generic delete method
     * @return boolean, the result of removing user
     */
    @Override
    public boolean remove(User user) {
        LOGGER.info("call user remove method");

        lock.lock();
        try {
            return set.remove(user);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param user generic update method
     * @return user, wrapped into optional
     */
    @Override
    public Optional<User> update(User user) {
        LOGGER.info("call user update method");

        lock.lock();
        try {
            Iterator<User> iterator = set.iterator();
            User us = null;
            while(iterator.hasNext()) {
                us = iterator.next();
                if (us.getId() == user.getId()) {
                    iterator.remove();
                    set.add(user);
                    us = user;
                    break;
                } else {
                    us = null;
                }
            }

            return Optional.ofNullable(us);
        } finally {
            lock.unlock();
        }
    }
}
