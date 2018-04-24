package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Hotel;
import com.epam.makedon.agency.repository.HotelRepository;
import com.epam.makedon.agency.service.HotelService;
import com.epam.makedon.agency.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class HotelServiceImpl implements HotelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    private HotelRepository hotelRepository;

    public HotelServiceImpl() {}

    @Autowired(required = false)
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Autowired(required = false)
    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public boolean add(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return hotelRepository.add(hotel);
    }

    @Override
    public Optional<Hotel> get(long id) {
        return hotelRepository.get(id);
    }

    @Override
    public boolean remove(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return hotelRepository.remove(hotel);
    }

    @Override
    public Optional<Hotel> update(Hotel hotel) {
        if (hotel == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return hotelRepository.update(hotel);
    }
}