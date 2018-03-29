package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.repository.impl.CountryRepositoryImpl;
import com.epam.makedon.agency.service.CountryService;
import com.epam.makedon.agency.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Override
    public boolean add(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return CountryRepositoryImpl.getInstance().add(country);
    }

    @Override
    public Optional<Country> get(long id) {
        return CountryRepositoryImpl.getInstance().get(id);
    }

    @Override
    public boolean remove(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return CountryRepositoryImpl.getInstance().remove(country);
    }

    @Override
    public Optional<Country> update(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return CountryRepositoryImpl.getInstance().update(country);
    }
}
