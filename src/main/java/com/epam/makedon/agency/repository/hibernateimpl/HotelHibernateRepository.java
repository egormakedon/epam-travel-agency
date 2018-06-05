package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class HotelHibernateRepository implements HotelRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class HotelHibernateRepository implements HotelRepository {

    /**
     * default constructor
     */
    public HotelHibernateRepository() {}

    /**
     * @param hotel object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(Hotel hotel) {
        return false;
    }

    /**
     * @param id to define and find hotel in repository
     * @return hotel object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<Hotel> get(long id) {
        return Optional.empty();
    }

    /**
     * @param hotel object to remove from repository
     * @return boolean result of hotel removing from repository
     */
    @Override
    public boolean remove(Hotel hotel) {
        return false;
    }

    /**
     * @param hotel object to update in repository
     * @return updated hotel object, wrapped in optional
     */
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        return Optional.empty();
    }
}
