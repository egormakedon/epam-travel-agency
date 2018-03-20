package repository;

import entity.Entity;

import java.util.Set;

/**
 * Interface {@code Collection} define CRUD methods.
 *
 * @param <T> generic interface define type of classes, which cane use this methods.
 * @author Yahor Makedon
 * @see repository
 * @version 1.0
 * @since version 1.0
 */
interface Collection<T extends Entity> {
    /**
     * @param entity generic add method
     */
    void add(T entity);

    /**
     * @return set of entities
     */
    Set<T> get();

    /**
     * @param entity generic delete method
     */
    void delete(T entity);

    /**
     * @param entity generic update method
     */
    void update(T entity);
}