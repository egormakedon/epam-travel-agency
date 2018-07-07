package com.epam.makedon.agency.agencydomain.service;

import com.epam.makedon.agency.agencydomain.domain.Entity;

import java.util.Optional;

/**
 * This interface define CRUD methods for service implementation classes
 *
 * @param <T> generic parameter, extends {@link Entity} interface.
 * @author Yahor Makedon
 * @version 1.0
 */

public interface Service<T extends Entity> {

    /**
     * Add operation
     *
     * @param entity {@link Entity}
     * @return true/false
     */
    boolean add(T entity);

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    Optional<T> get(long id);

    /**
     * Remove operation
     *
     * @param entity {@link Entity}
     * @return true/false
     */
    boolean remove(T entity);

    /**
     * Update operation
     *
     * @param entity {@link Entity}
     * @return object, wrapped in {@link Optional} class
     */
    Optional<T> update(T entity);
}