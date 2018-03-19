package repository;

import entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

class CollectionSet<T extends Entity> implements Collection<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionSet.class);
    private static ReentrantLock lock = new ReentrantLock();

    private Set<T> entitySet;

    CollectionSet() {
        entitySet = new HashSet<T>();
    }

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

    @Override
    public Set<T> get() {
        lock.lock();
        try {
            LOGGER.info("get set");
            return entitySet;
        } finally {
            lock.unlock();
        }
    }

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

    @Override
    public void update(T entity) {
        lock.lock();
        try {
            long id = entity.getId();
            Iterator<T> iterator = entitySet.iterator();
            while(iterator.hasNext()) {
                T ent = iterator.next();
                if (ent.getId() == id) {
                    iterator.remove();
                    entitySet.add(entity);
                    LOGGER.info("update " + entity.getClass());
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }
}