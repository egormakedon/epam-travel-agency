package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.Entity;

import java.util.Set;

/**
 * Interface {@code StorageOperation} define CRUD methods.
 *
 * @param <T> generic interface define type of classes, which cane use this methods.
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 1.0
 * @since version 1.0
 */
interface StorageOperation<T extends Entity> {
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