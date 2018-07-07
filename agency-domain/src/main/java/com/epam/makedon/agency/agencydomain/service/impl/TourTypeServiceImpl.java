package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourTypeRepository;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.TourTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for {@link TourType} class,
 * implements {@link TourTypeService} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Service
@Profile("service")

public class TourTypeServiceImpl implements TourTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TourTypeServiceImpl.class);

    @Autowired(required = false)
    private TourTypeRepository tourTypeRepository;

    /**
     * default constructor
     */
    public TourTypeServiceImpl() {}

    /**
     * Add operation,
     * supported with {@link Transactional}.
     *
     * @param tourType {@link TourType}
     * @return true/false
     * @throws ServiceException cause param is null
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("tourType argument in add method is null");
            throw new ServiceException("tourType argument in add method is null");
        }

        return tourTypeRepository.add(tourType);
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
    public Optional<TourType> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        return tourTypeRepository.get(id);
    }

    /**
     * Remove operation,
     * supported with {@link Transactional}.
     *
     * @param tourType {@link TourType}
     * @return true/false
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("tourType argument in remove method is null");
            throw new ServiceException("tourType argument in remove method is null");
        }

        return tourTypeRepository.remove(tourType);
    }

    /**
     * Update operation,
     * supported with {@link Transactional}.
     *
     * @param tourType {@link TourType}
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<TourType> update(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("tourType argument in update method is null");
            throw new ServiceException("tourType argument in update method is null");
        }

        return tourTypeRepository.update(tourType);
    }
}