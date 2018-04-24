package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.Country;
import com.epam.makedon.agency.repository.CountryRepository;
import com.epam.makedon.agency.service.CountryService;
import com.epam.makedon.agency.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class CountryServiceImpl implements CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Autowired
    private CountryRepository countryRepository;

    public CountryServiceImpl() {}

    @Autowired(required = false)
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Autowired(required = false)
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean add(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return countryRepository.add(country);
    }

    @Override
    public Optional<Country> get(long id) {
        return countryRepository.get(id);
    }

    @Override
    public boolean remove(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return countryRepository.remove(country);
    }

    @Override
    public Optional<Country> update(Country country) {
        if (country == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return countryRepository.update(country);
    }
}
