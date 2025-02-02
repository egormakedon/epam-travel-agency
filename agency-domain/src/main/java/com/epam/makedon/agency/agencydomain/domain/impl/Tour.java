package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.CountryConverter;
import com.epam.makedon.agency.agencydomain.domain.Entity;
import com.epam.makedon.agency.agencydomain.domain.TourTypeConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

/**
 * This entity class describe information about Tour,
 * implements {@link Entity} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Data
@javax.persistence.Entity(name = "Tour")
@Table(name = "tour")
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Document(collection = "tour")

public class Tour implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    @NotNull
    private long id;

    @Column(name = "tour_photo")
    @NotNull
    @Size(max = 255)
    private String photo;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "tour_date")
    @NotNull
    private LocalDate date;

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    @Column(name = "tour_duration")
    @NotNull
    private Duration duration;

    @Basic
    @Convert(converter = CountryConverter.class)
    @Column(name = "fk_country_id")
    @NotNull
    private Country country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_hotel_id")
    @NotNull
    private Hotel hotel;

    @Basic
    @Convert(converter = TourTypeConverter.class)
    @Column(name = "fk_tour_type_id")
    @NotNull
    private TourType type;

    @Column(name = "tour_description")
    @NotNull
    @Size(min = 1)
    private String description;

    @Column(name = "tour_cost")
    @NotNull
    @Min(value = 0)
    private BigDecimal cost;

    @Version
    @org.springframework.data.annotation.Version
    @Column(name = "tour_version")
    private Integer version;
}