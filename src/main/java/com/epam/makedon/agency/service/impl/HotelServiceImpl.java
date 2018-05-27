package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import com.epam.makedon.agency.service.HotelService;
import com.epam.makedon.agency.service.ServiceException;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class HotelServiceImpl implements HotelService.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @since version 1.0
 */
@Service
@Profile("service")
public class HotelServiceImpl implements HotelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    @Setter
    private HotelRepository hotelRepository;

    public HotelServiceImpl() {}

    /**
     * Hotel add method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param hotel object, which be adding to repository
     * @return boolean, result of adding
     * @throws ServiceException cause param is null or exception of adding
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("hotel argument in add method is null");
            throw new ServiceException("hotel argument in add method is null");
        }

        try {
            return hotelRepository.add(hotel);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Hotel get method. Supported with @Transactional (Isolation.READ_COMMITTED, Propagation.REQUIRED)
     *
     * @param id to identify and find hotel in repository
     * @return hotel object, wrapped in Optional, cause null
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Hotel> get(long id) {
        try {
            return hotelRepository.get(id);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Hotel remove method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param hotel object, which be removing from repository
     * @return boolean result of removing
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("hotel argument in remove method is null");
            throw new ServiceException("hotel argument in remove method is null");
        }

        try {
            return hotelRepository.remove(hotel);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Hotel update method. Supported with @Transactional (Isolation.READ_UNCOMMITTED, Propagation.REQUIRED)
     *
     * @param hotel object, which be updating in repository
     * @return hotel object, wrapped in Optional, cause null
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Hotel> update(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("hotel argument in update method is null");
            throw new ServiceException("hotel argument in update method is null");
        }

        try {
            return hotelRepository.update(hotel);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }
}