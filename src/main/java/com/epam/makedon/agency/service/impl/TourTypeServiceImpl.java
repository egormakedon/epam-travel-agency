package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourTypeRepository;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourTypeService;
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
 * Class TourTypeServiceImpl implements TourTypeService.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @since version 1.0
 */
@Service
@Profile("service")
public class TourTypeServiceImpl implements TourTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourTypeServiceImpl.class);

    @Autowired
    @Setter
    private TourTypeRepository tourTypeRepository;

    /**
     * default constructor
     */
    public TourTypeServiceImpl() {}

    /**
     * TourType add method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param tourType object, which be adding to repository
     * @return boolean, result of adding
     * @throws ServiceException cause param is null or exception of adding
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("tourType argument in add method is null");
            throw new ServiceException("tourType argument in add method is null");
        }

        try {
            return tourTypeRepository.add(tourType);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * TourType get method. Supported with @Transactional (Isolation.READ_COMMITTED, Propagation.REQUIRED)
     *
     * @param id to identify and find tourType in repository
     * @return tourType object, wrapped in Optional, cause null
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<TourType> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        try {
            return tourTypeRepository.get(id);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * TourType remove method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param tourType object, which be removing from repository
     * @return boolean result of removing
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("tourType argument in remove method is null");
            throw new ServiceException("tourType argument in remove method is null");
        }

        try {
            return tourTypeRepository.remove(tourType);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * TourType update method. Supported with @Transactional (Isolation.READ_UNCOMMITTED, Propagation.REQUIRED)
     *
     * @param tourType object, which be updating in repository
     * @return tourType object, wrapped in Optional, cause null
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<TourType> update(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("tourType argument in update method is null");
            throw new ServiceException("tourType argument in update method is null");
        }

        try {
            return tourTypeRepository.update(tourType);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }
}
