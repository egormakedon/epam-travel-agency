package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code Storage} is generic class, that implemets interface {@code CrudInterface}
 *
 * @author Yahor Makedon
 * @see CrudInterface
 * @version 1.0
 * @since version 1.0
 * @param <T> generic class, which define, what type of entities will store.
 */
public class Storage<T extends Entity> implements CrudInterface<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Storage.class);
    private static ReentrantLock lock = new ReentrantLock();

    private Set<T> entitySet;

    Storage() {
        entitySet = new HashSet<T>();
    }

    /**
     * Override method from interface {@code CrudInterface}
     * Thread-safe, using locks
     *
     * @param entity generic add method
     */
    @Override
    public void add(T entity) {
        lock.lock();
        try {
            entitySet.add(entity);
            LOGGER.info("added " + entity.getClass());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Override method from interface {@code CrudInterface}
     * Thread-safe, using locks
     *
     * @return set of entities
     */
    @Override
    public Set<T> get() {
        lock.lock();
        try {
            LOGGER.info("get " + entitySet.getClass());
            return entitySet;
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param id of entity
     * @return entity, wrapper in optional
     */
    public T get(long id) {
        lock.lock();
        try {
            Iterator<T> iterator = entitySet.iterator();
            while (iterator.hasNext()) {
                T entity = iterator.next();
                if (id == entity.getId()) {
                    LOGGER.info("get " + entity);
                    return entity;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Override method from interface {@code CrudInterface}
     * Thread-safe, using locks
     *
     * @param entity generic delete method
     */
    @Override
    public void delete(T entity) {
        lock.lock();
        try {
            entitySet.remove(entity);
            LOGGER.info("remove " + entity.getClass());
        } finally {
            lock.unlock();
        }
    }

    /**
     * Override method from interface {@code CrudInterface}
     * Thread-safe, using locks
     *
     * @param entity generic update method
     */
    @Override
    public void update(T entity) {
        lock.lock();
        try {
            long id = entity.getId();
            Iterator<T> iterator = entitySet.iterator();
            T ent = null;
            boolean result = false;
            while(iterator.hasNext()) {
                ent = iterator.next();
                if (ent.getId() == id) {
                    result = true;
                    break;
                }
            }

            if (result) {
                entitySet.remove(ent);
                entitySet.add(entity);
                LOGGER.info("update " + entity.getClass());
            }
        } finally {
            lock.unlock();
        }
    }
}