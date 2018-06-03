package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
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
    private long id;

    @Column(name = "tour_photo")
    private String photo;

    @Column(name = "tour_date")
    private LocalDate date;

    @Column(name = "tour_duration")
    private Duration duration;

//    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fk_country_id")
//    @ManyToOne
    @JoinColumn(name = "fk_country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "fk_hotel_id")
    private Hotel hotel;


//    @Enumerated()
    @Column(name = "fk_tour_type_id")
//    @ManyToOne
    @JoinColumn(name = "fk_tour_type_id")
    private TourType type;

    @Column(name = "tour_description")
    private String description;

    @Column(name = "tour_cost")
    private BigDecimal cost;

//    @Version
//    private Integer version;
}