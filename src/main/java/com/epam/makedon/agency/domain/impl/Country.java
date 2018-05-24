package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum {@code Country} is Entity class.
 * This enum consists of country names and their id.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @version 2.0
 * @since version 1.0
 */
public enum Country implements Entity {
    BELARUS(1), RUSSIA(2), POLAND(3), SPAIN(4), ENGLAND(5), UKRAINE(6), USA(7), CHINA(8);

    private long id;

    Country(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}