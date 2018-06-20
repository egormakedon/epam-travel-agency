package com.epam.makedon.agency.agencydomain.service;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface TourService mark service with Tour domain.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.service
 * @since version 1.0
 */
public interface TourService extends Service<Tour> {
    default List<Tour> findByCriteria(LocalDate date, Duration duration, Country country,
                              Byte stars, TourType type, BigDecimal cost) {
        return null;
    }
}