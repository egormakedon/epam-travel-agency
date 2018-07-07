package com.epam.makedon.agency.agencydomain.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.TourType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This class is set mapping between {@link TourType} class and database using hibernate framework,
 * implements {@link AttributeConverter} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Converter

public class TourTypeConverter implements AttributeConverter<TourType, Long> {

    @Override
    public Long convertToDatabaseColumn(TourType tourType) {
        if (tourType == null) {
            return null;
        }
        return tourType.getId();
    }

    @Override
    public TourType convertToEntityAttribute(Long aLong) {
        if (aLong == null) {
            return null;
        }
        return TourType.fromCode(aLong);
    }
}
