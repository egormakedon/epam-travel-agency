package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.repository.TourRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class TourHibernateRepository implements TourRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class TourHibernateRepository implements TourRepository {

    /**
     * default constructor
     */
    public TourHibernateRepository() {}

    /**
     * @param tour object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Tour tour) {
        return false;
    }

    /**
     * @param id to define and find tour in repository
     * @return tour object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Tour> get(long id) {
        return Optional.empty();
    }

    /**
     * @param tour object to remove from repository
     * @return boolean result of tour removing from repository
     */
    @Override
    public boolean remove(Tour tour) {
        return false;
    }

    /**
     * @param tour object to update in repository
     * @return updated tour object, wrapped in optional
     */
    @Override
    public Optional<Tour> update(Tour tour) {
        return Optional.empty();
    }
}
