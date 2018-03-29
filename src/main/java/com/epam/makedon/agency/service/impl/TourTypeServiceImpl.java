package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.TourType;
import com.epam.makedon.agency.repository.impl.TourTypeRepositoryImpl;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public class TourTypeServiceImpl implements TourTypeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TourTypeServiceImpl.class);

    @Override
    public boolean add(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return TourTypeRepositoryImpl.getInstance().add(tourType);
    }

    @Override
    public Optional<TourType> get(long id) {
        return TourTypeRepositoryImpl.getInstance().get(id);
    }

    @Override
    public boolean remove(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return TourTypeRepositoryImpl.getInstance().remove(tourType);
    }

    @Override
    public Optional<TourType> update(TourType tourType) {
        if (tourType == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return TourTypeRepositoryImpl.getInstance().update(tourType);
    }
}
