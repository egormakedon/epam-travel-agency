package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.repository.CountryRepository;
import com.epam.makedon.agency.service.CountryService;
import com.epam.makedon.agency.service.ServiceException;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @since version 1.0
 */
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Autowired
    @Setter
    private CountryRepository countryRepository;

    public CountryServiceImpl() {}

    /**
     * @param country
     * @return
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<Country> get(long id) {
        try {
            return countryRepository.get(id);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return countryRepository.remove(country);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Country> update(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return countryRepository.update(country);
    }
}
