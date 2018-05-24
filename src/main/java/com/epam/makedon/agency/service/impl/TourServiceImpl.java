package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.repository.TourRepository;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);

    @Autowired
    private TourRepository tourRepository;

    @Autowired(required = false)
    public TourServiceImpl() {}

    @Autowired(required = false)
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void setTourRepository(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourRepository.add(tour);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Tour> get(long id) {
        return tourRepository.get(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourRepository.remove(tour);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Tour> update(Tour tour) {
        if (tour == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourRepository.update(tour);
    }
}
