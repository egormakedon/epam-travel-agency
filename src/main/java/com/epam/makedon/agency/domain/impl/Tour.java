package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.CountryConverter;
import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    @NotNull
    private long id;

    @Column(name = "tour_photo")
    @NotNull
    @Size(min = 2, max = 100)
    private String photo;

    @Column(name = "tour_date")
    @NotNull
    private LocalDate date;

    @Column(name = "tour_duration")
    @NotNull
    private Duration duration;

    @Basic
    @Convert(converter = CountryConverter.class)
    @Column(name = "fk_country_id")
    @NotNull
    private Country country;

    @ManyToOne
    @JoinColumn(name = "fk_hotel_id")
    @NotNull
    private Hotel hotel;

    @Basic
    @Convert(converter = TourType.class)
    @Column(name = "fk_tour_type_id")
    @NotNull
    private TourType type;

    @Column(name = "tour_description")
    @NotNull
    @Size(min = 2)
    private String description;

    @Column(name = "tour_cost")
    @NotNull
    private BigDecimal cost;

    @Version
    @Column(name = "tour_version")
    private Integer version;
}