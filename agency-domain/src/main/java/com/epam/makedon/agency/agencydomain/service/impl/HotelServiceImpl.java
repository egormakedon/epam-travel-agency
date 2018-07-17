package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.repository.HotelRepository;
import com.epam.makedon.agency.agencydomain.service.HotelService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for {@link Hotel} class,
 * implements {@link HotelService} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Service
@Profile("service")

public class HotelServiceImpl implements HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    private HotelRepository hotelRepository;

    /**
     * default constructor
     */
    public HotelServiceImpl() {}

    /**
     * Add operation,
     * supported with {@link Transactional}.
     *
     * @param hotel {@link Hotel}
     * @return true/false
     * @throws ServiceException cause param is null
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("hotel argument in add method is null");
            throw new ServiceException("hotel argument in add method is null");
        }

        return hotelRepository.add(hotel);
    }

    /**
     * Get/find/take operation,
     * supported with {@link Transactional}.
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public Optional<Hotel> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        return hotelRepository.get(id);
    }

    /**
     * Remove operation,
     * supported with {@link Transactional}.
     *
     * @param hotel {@link Hotel}
     * @return true/false
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("hotel argument in remove method is null");
            throw new ServiceException("hotel argument in remove method is null");
        }

        return hotelRepository.remove(hotel);
    }

    /**
     * Update operation,
     * supported with {@link Transactional}.
     *
     * @param hotel {@link Hotel}
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("hotel argument in update method is null");
            throw new ServiceException("hotel argument in update method is null");
        }

        return hotelRepository.update(hotel);
    }
}