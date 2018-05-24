package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.domain.Entity;

import java.io.Serializable;
import java.util.Optional;

/**
 * Interface {@code Repository} define CRUD methods.
 * since version 3.0 added @Transactional support
 *
 * @param <T> generic interface define type of classes, which cane use this methods. It's domain classes.
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 3.0
 * @since version 1.0
 */
public interface Repository<T extends Entity> extends Serializable, Cloneable {

    /**
     * supported with transactional
     *
     * @param entity object, which be insert into repository
     * @return the result of adding (true/false)
     */
    boolean add(T entity);

    /**
     * supported with transactional
     *
     * @param id to define and find object
     * @return domain, wrapper in optional, cause can return null
     */
    Optional<T> get(long id);

    /**
     * supported with transactional
     *
     * @param entity generic delete method
     * @return the result of removing (true/false)
     */
    boolean remove(T entity);

    /**
     * supported with transactional
     *
     * @param entity generic update method
     * @return updated domain, wrapper in optional, cause can return null
     */
    Optional<T> update(T entity);
}