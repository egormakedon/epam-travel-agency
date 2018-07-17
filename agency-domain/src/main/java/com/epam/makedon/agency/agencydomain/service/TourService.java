package com.epam.makedon.agency.agencydomain.service;

import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface markup services for {@link Tour} class,
 * extends {@link Service} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public interface TourService extends Service<Tour> {

    List<Tour> findByCriteria(LocalDate date, Duration duration, Country country,
                              Byte stars, TourType type, BigDecimal cost);
    List<Tour> findAll();
}