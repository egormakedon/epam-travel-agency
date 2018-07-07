package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.repository.CountryRepository;
import com.epam.makedon.agency.agencydomain.service.CountryService;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for {@link Country} class,
 * implements {@link CountryService} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Service
@Profile("service")

public class CountryServiceImpl implements CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Autowired(required = false)
    private CountryRepository countryRepository;

    /**
     * default constructor
     */
    public CountryServiceImpl() {}

    /**
     * Add operation,
     * supported with {@link Transactional}.
     *
     * @param country {@link Country}
     * @return true/false
     * @throws ServiceException cause param is null
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(Country country) {
        if (country == null) {
            LOGGER.error("country argument in add method is null");
            throw new ServiceException("country argument in add method is null");
        }

        return countryRepository.add(country);
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
    public Optional<Country> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        return countryRepository.get(id);
    }

    /**
     * Remove operation,
     * supported with {@link Transactional}.
     *
     * @param country {@link Country}
     * @return true/false
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Country country) {
        if (country == null) {
            LOGGER.error("country argument in remove method is null");
            throw new ServiceException("country argument in remove method is null");
        }

        return countryRepository.remove(country);
    }

    /**
     * Update operation,
     * supported with {@link Transactional}.
     *
     * @param country {@link Country}
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Country> update(Country country) {
        if (country == null) {
            LOGGER.error("country argument in update method is null");
            throw new ServiceException("country argument in update method is null");
        }

        return countryRepository.update(country);
    }
}