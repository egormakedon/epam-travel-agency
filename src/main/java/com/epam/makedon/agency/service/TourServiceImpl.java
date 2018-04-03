package com.epam.makedon.agency.service;

import com.epam.makedon.agency.entity.impl.Tour;
import com.epam.makedon.agency.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);

    private TourRepository tourRepository;

    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public boolean add(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourRepository.add(tour);
    }

    @Override
    public Optional<Tour> get(long id) {
        return tourRepository.get(id);
    }

    @Override
    public boolean remove(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourRepository.remove(tour);
    }

    @Override
    public Optional<Tour> update(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourRepository.update(tour);
    }
}
