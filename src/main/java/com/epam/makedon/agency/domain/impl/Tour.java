package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Table;
import javax.persistence.Version;
import java.awt.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Class Tour is Entity class.
 * It stores information about tour data.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @since version 1.0
 */
@Data
@javax.persistence.Entity(name = "Tour")
@Table(name = "tour")
@OptimisticLocking(type = OptimisticLockType.VERSION)
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

    @Version
    private Integer version;
}