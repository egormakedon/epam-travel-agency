package com.epam.makedon.agency.agencydomain.repository;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface TourRepository mark repository with Tour domain.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.repository
 * @since version 1.0
 */
public interface TourRepository extends Repository<Tour> {
    default List<Tour> findByCriteria(LocalDate date, Duration duration, Country country,
                                     Byte stars, TourType type, BigDecimal cost) {
        return null;
    }
}