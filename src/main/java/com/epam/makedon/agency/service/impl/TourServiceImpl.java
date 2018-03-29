package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.repository.impl.TourRepositoryImpl;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);

    @Override
    public boolean add(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return TourRepositoryImpl.getInstance().add(tour);
    }

    @Override
    public Optional<Tour> get(long id) {
        return TourRepositoryImpl.getInstance().get(id);
    }

    @Override
    public boolean remove(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return TourRepositoryImpl.getInstance().remove(tour);
    }

    @Override
    public Optional<Tour> update(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return TourRepositoryImpl.getInstance().update(tour);
    }
}
