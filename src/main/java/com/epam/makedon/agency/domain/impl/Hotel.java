package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class Hotel is Entity class.
 * This class stores information about hotels, which uses in project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @since version 1.0
 */
@Data
@javax.persistence.Entity(name = "Hotel")
@Table(name = "hotel")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Hotel implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    @NotNull
    private long id;

    @Column(name = "hotel_name")
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Column(name = "hotel_phone")
    @NotNull
    @Size(min = 2, max = 25)
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