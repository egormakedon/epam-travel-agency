package repository;

import entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code CollectionSet} is generic class, that implemets interface {@code Collection}
 *
 * @author Yahor Makedon
 * @see repository.Collection
 * @version 1.0
 * @since version 1.0
 * @param <T> generic class, which define, what type of entities will store.
 */
class CollectionSet<T extends Entity> implements Collection<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionSet.class);
    private static ReentrantLock lock = new ReentrantLock();

    private Set<T> entitySet;

    CollectionSet() {
        entitySet = new HashSet<T>();
    }

    /**
     * Override method from interface {@code Collection}
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
     * Override method from interface {@code Collection}
     * Thread-safe, using locks
     *
     * @return set of entities
     */
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

    /**
     * Override method from interface {@code Collection}
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
     * Override method from interface {@code Collection}
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