package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;

import java.awt.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Class {@code Tour} is Entity class.
 * It stores information about tour data.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @version 3.0
 * @since version 1.0
 */
@Data
public class Tour implements Entity {
    private long id;
    private Image photo;
    private LocalDate date;
    private Duration duration;
    private Country country;
    private Hotel hotel;
    private TourType type;
    private String description;
    private BigDecimal cost;
}
