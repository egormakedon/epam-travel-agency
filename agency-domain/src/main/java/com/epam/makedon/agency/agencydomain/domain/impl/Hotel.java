package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This entity class describe information about Hotel,
 * implements {@link Entity} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Data
@javax.persistence.Entity(name = "Hotel")
@Table(name = "hotel")
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Document(collection = "hotel")

public class Hotel implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    @NotNull
    private long id;

    @Column(name = "hotel_name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "hotel_phone")
    @NotNull
    @Size(max = 100)
    private String phone;

    @Column(name = "hotel_stars")
    @NotNull
    @Min(1)
    @Max(5)
    private byte stars;

    @Version
    @Column(name = "hotel_version")
    private Integer version;
}