package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourRepository;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for {@link Tour} class,
 * implements {@link TourService} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Service
@Profile("service")

public class TourServiceImpl implements TourService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);

    @Autowired
    private TourRepository tourRepository;

    /**
     * default constructor
     */
    public TourServiceImpl() {}

    /**
     * Add operation,
     * supported with {@link Transactional}.
     *
     * @param tour {@link Tour}
     * @return true/false
     * @throws ServiceException cause param is null
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Tour tour) {
        if (tour == null) {
            LOGGER.error("tour argument in add method is null");
            throw new ServiceException("tour argument in add method is null");
        }

        return tourRepository.add(tour);
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
    public Optional<Tour> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        return tourRepository.get(id);
    }

    /**
     * Find tour by criteria's,
     * supported with {@link Transactional}.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public List<Tour> findByCriteria(LocalDate date, Duration duration, Country country,
                                     Byte stars, TourType type, BigDecimal cost) {

        return tourRepository.findByCriteria(date, duration, country, stars, type, cost);
    }

    /**
     * Find all tour's,
     * supported with {@link Transactional}.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public List<Tour> findAll() {

        return tourRepository.findAll();
    }

    /**
     * Remove operation,
     * supported with {@link Transactional}.
     *
     * @param tour {@link Tour}
     * @return true/false
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Tour tour) {
        if (tour == null) {
            LOGGER.error("tour argument in remove method is null");
            throw new ServiceException("tour argument in remove method is null");
        }

        return tourRepository.remove(tour);
    }

    /**
     * Update operation,
     * supported with {@link Transactional}.
     *
     * @param tour {@link Tour}
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Tour> update(Tour tour) {
        if (tour == null) {
            LOGGER.error("tour argument in update method is null");
            throw new ServiceException("tour argument in update method is null");
        }

        return tourRepository.update(tour);
    }
}