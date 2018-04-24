package com.epam.makedon.agency.service;

import com.epam.makedon.agency.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public interface Service<T extends Entity> {

    /**
     * supported with transactional
     * @param entity object, which be insert into repository
     * @return the result of adding (true/false)
     */
    @Transactional
    boolean add(T entity);

    /**
     * supported with transactional
     * @param id to define and find object
     * @return entity, wrapper in optional, cause can return null
     */
    @Transactional
    Optional<T> get(long id);

    /**
     * supported with transactional
     * @param entity generic delete method
     * @return the result of removing (true/false)
     */
    @Transactional
    boolean remove(T entity);

    /**
     * supported with transactional
     * @param entity generic update method
     * @return updated entity, wrapper in optional, cause can return null
     */
    @Transactional
    Optional<T> update(T entity);
}