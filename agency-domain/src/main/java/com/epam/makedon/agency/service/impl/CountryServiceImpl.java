package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.repository.CountryRepository;
import com.epam.makedon.agency.service.CountryService;
import com.epam.makedon.agency.service.ServiceException;
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
 * Class CountryServiceImpl implements CountryService.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @since version 1.0
 */
@Service
@Profile("service")
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Autowired
    @Setter
    private CountryRepository countryRepository;

    /**
     * default constructor
     */
    public CountryServiceImpl() {}

    /**
     * Country add method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param country object, which be adding to repository
     * @return boolean, result of adding
     * @throws ServiceException cause param is null or exception of adding
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Country country) {
        if (country == null) {
            LOGGER.error("country argument in add method is null");
            throw new ServiceException("country argument in add method is null");
        }

        try {
            return countryRepository.add(country);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Country get method. Supported with @Transactional (Isolation.READ_COMMITTED, Propagation.REQUIRED)
     *
     * @param id to identify and find country in repository
     * @return country object, wrapped in Optional, cause null
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Country> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        try {
            return countryRepository.get(id);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Country remove method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param country object, which be removing from repository
     * @return boolean result of removing
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Country country) {
        if (country == null) {
            LOGGER.error("country argument in remove method is null");
            throw new ServiceException("country argument in remove method is null");
        }

        try {
            return countryRepository.remove(country);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Country update method. Supported with @Transactional (Isolation.READ_UNCOMMITTED, Propagation.REQUIRED)
     *
     * @param country object, which be updating in repository
     * @return country object, wrapped in Optional, cause null
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Country> update(Country country) {
        if (country == null) {
            LOGGER.error("country argument in update method is null");
            throw new ServiceException("country argument in update method is null");
        }

        try {
            return countryRepository.update(country);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }
}
