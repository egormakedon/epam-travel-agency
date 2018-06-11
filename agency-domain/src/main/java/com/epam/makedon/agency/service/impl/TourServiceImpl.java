package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.repository.TourRepository;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourService;
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
 * Class TourServiceImpl implements TourService
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @since version 1.0
 */
@Service
@Profile("service")
public class TourServiceImpl implements TourService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourServiceImpl.class);

    @Autowired
    @Setter
    private TourRepository tourRepository;

    /**
     * default constructor
     */
    public TourServiceImpl() {}

    /**
     * Tour add method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param tour object, which be adding to repository
     * @return boolean, result of adding
     * @throws ServiceException cause param is null or exception of adding
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Tour tour) {
        if (tour == null) {
            LOGGER.error("tour argument in add method is null");
            throw new ServiceException("tour argument in add method is null");
        }

        try {
            return tourRepository.add(tour);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Tour get method. Supported with @Transactional (Isolation.READ_COMMITTED, Propagation.REQUIRED)
     *
     * @param id to identify and find tour in repository
     * @return tour object, wrapped in Optional, cause null
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Tour> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        try {
            return tourRepository.get(id);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Tour remove method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param tour object, which be removing from repository
     * @return boolean result of removing
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Tour tour) {
        if (tour == null) {
            LOGGER.error("tour argument in remove method is null");
            throw new ServiceException("tour argument in remove method is null");
        }

        try {
            return tourRepository.remove(tour);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Tour update method. Supported with @Transactional (Isolation.READ_UNCOMMITTED, Propagation.REQUIRED)
     *
     * @param tour object, which be updating in repository
     * @return tour object, wrapped in Optional, cause null
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Tour> update(Tour tour) {
        if (tour == null) {
            LOGGER.error("tour argument in update method is null");
            throw new ServiceException("tour argument in update method is null");
        }

        try {
            return tourRepository.update(tour);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }
}
