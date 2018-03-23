package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;

/**
 * Enum {@code Country} is Entity class.
 * This enum consists of country names and their id.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 1.0
 * @since version 1.0
 */
public enum Country implements Entity {
    BELARUS(1), RUSSIA(2), POLAND(3), SPAIN(4), ENGLAND(5), UKRAINE(6), USA(7), CHINA(8);

    /**
     * Field {@code id} store id of country.
     */
    private long id;

    Country(long id) {
        this.id = id;
    }

    /**
     * @return id of country, type long.
     */
    public long getId() {
        return id;
    }
}