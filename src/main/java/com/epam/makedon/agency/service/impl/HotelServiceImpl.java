package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.impl.HotelRepositoryImpl;
import com.epam.makedon.agency.service.HotelService;
import com.epam.makedon.agency.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public class HotelServiceImpl implements HotelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Override
    public boolean add(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return HotelRepositoryImpl.getInstance().add(hotel);
    }

    @Override
    public Optional<Hotel> get(long id) {
        return HotelRepositoryImpl.getInstance().get(id);
    }

    @Override
    public boolean remove(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return HotelRepositoryImpl.getInstance().remove(hotel);
    }

    @Override
    public Optional<Hotel> update(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return HotelRepositoryImpl.getInstance().update(hotel);
    }
}