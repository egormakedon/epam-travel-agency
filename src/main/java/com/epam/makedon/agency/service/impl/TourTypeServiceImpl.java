package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourTypeRepository;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourTypeService;
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
public class TourTypeServiceImpl implements TourTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourTypeServiceImpl.class);

    @Autowired
    private TourTypeRepository tourTypeRepository;

    public TourTypeServiceImpl() {}

    @Autowired(required = false)
    public TourTypeServiceImpl(TourTypeRepository tourTypeRepository) {
        this.tourTypeRepository = tourTypeRepository;
    }

    @Autowired(required = false)
    public void setTourTypeRepository(TourTypeRepository tourTypeRepository) {
        this.tourTypeRepository = tourTypeRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourTypeRepository.add(tourType);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<TourType> get(long id) {
        return tourTypeRepository.get(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourTypeRepository.remove(tourType);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<TourType> update(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return tourTypeRepository.update(tourType);
    }
}
