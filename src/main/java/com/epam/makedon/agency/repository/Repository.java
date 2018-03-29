package com.epam.makedon.agency.repository;

import com.epam.makedon.agency.entity.Entity;

import java.io.Serializable;
import java.util.Optional;

/**
 * Interface {@code Repository} define CRUD methods.
 *
 * @param <T> generic interface define type of classes, which cane use this methods. It's entity classes.
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @version 2.0
 * @since version 1.0
 */
public interface Repository<T extends Entity> extends Serializable, Cloneable {

    /**
     * @param entity object, which be insert into repository
     * @return the result of adding (true/false)
     */
    boolean add(T entity);

    /**
     * @param id to define and find object
     * @return entity, wrapper in optional, cause can return null
     */
    Optional<T> get(long id);

    /**
     * @param entity generic delete method
     * @return the result of removing (true/false)
     */
    boolean remove(T entity);

    /**
     * @param entity generic update method
     * @return updated entity, wrapper in optional, cause can return null
     */
    Optional<T> update(T entity);
}