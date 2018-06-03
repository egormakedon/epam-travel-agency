package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;

import javax.persistence.*;

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
public class Hotel implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private long id;

    @Column(name = "hotel_name")
    private String name;

    @Column(name = "hotel_phone")
    private String phone;

    @Column(name = "hotel_stars")
    private byte stars;
}