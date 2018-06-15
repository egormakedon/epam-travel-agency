package com.epam.makedon.agency.agencydomain.service;

import com.epam.makedon.agency.agencydomain.domain.Entity;

import java.util.Optional;

/**
 * Service interface
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.service
 * @since version 1.0
 */
public interface Service<T extends Entity> {

    /**
     * @param entity object, which be inserting into repository
     * @return boolean result of inserting
     */
    boolean add(T entity);

    /**
     * @param id to define and find object
     * @return domain, wrapped in optional, cause can return null
     */
    Optional<T> get(long id);

    /**
     * @param entity object, which be removing from repository
     * @return boolean result of removing
     */
    boolean remove(T entity);

    /**
     * @param entity object, which be updating in repository
     * @return domain, wrapped in optional, cause can return null
     */
    Optional<T> update(T entity);
}