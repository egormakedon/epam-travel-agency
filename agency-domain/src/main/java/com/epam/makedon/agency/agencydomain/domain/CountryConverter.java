package com.epam.makedon.agency.agencydomain.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This class is set mapping between {@link Country} class and database using hibernate framework,
 * implements {@link AttributeConverter} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Converter

public class CountryConverter implements AttributeConverter<Country, Long> {

    @Override
    public Long convertToDatabaseColumn(Country country) {
        if (country == null) {
            return null;
        }
        return country.getId();
    }

    @Override
    public Country convertToEntityAttribute(Long aLong) {
        if (aLong == null) {
            return null;
        }
        return Country.fromCode(aLong);
    }
}