package com.epam.makedon.agency.agencydomain.repository;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface markup repositories for {@link Tour} class,
 * extends {@link Repository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public interface TourRepository extends Repository<Tour> {

    default List<Tour> findByCriteria(LocalDate date, Duration duration, Country country,
                                     Byte stars, TourType type, BigDecimal cost) {
        return null;
    }

    default List<Tour> findAll() {

        return null;
    }
}