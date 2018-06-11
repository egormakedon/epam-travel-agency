package com.epam.makedon.agency.domain;

import com.epam.makedon.agency.domain.impl.Country;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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