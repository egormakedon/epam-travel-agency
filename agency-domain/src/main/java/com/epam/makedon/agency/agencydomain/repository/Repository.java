package com.epam.makedon.agency.agencydomain.repository;

import com.epam.makedon.agency.agencydomain.domain.Entity;

import java.util.Optional;

/**
 * Interface Repository define CRUD methods.
 *
 * @param <T> generic interface define type of classes, which cane use this methods. It's domain classes.
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 1.0
 */
public interface Repository<T extends Entity> {

    /**
     * @param entity object, which be insert into repository
     * @return the result of adding (true/false)
     */
    boolean add(T entity);

    /**
     * @param id to define and find object
     * @return domain, wrapper in optional, cause can return null
     */
    Optional<T> get(long id);

    /**
     * @param entity generic delete method
     * @return the result of removing (true/false)
     */
    boolean remove(T entity);

    /**
     * @param entity generic update method
     * @return updated domain, wrapper in optional, cause can return null
     */
    Optional<T> update(T entity);
}