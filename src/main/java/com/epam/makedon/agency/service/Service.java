package com.epam.makedon.agency.service;

import com.epam.makedon.agency.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * added @Transactional support
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public interface Service<T extends Entity> {

    /**
     * added @Transactional support
     *
     * @param entity object, which be insert into repository
     * @return the result of adding (true/false)
     */
    boolean add(T entity);

    /**
     * added @Transactional support
     *
     * @param id to define and find object
     * @return entity, wrapper in optional, cause can return null
     */
    Optional<T> get(long id);

    /**
     * added @Transactional support
     *
     * @param entity generic delete method
     * @return the result of removing (true/false)
     */
    boolean remove(T entity);

    /**
     * added @Transactional support
     *
     * @param entity generic update method
     * @return updated entity, wrapper in optional, cause can return null
     */
    Optional<T> update(T entity);
}