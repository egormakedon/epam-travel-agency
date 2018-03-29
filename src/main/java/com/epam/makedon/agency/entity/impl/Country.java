package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.NotSupportedOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum {@code Country} is Entity class.
 * This enum consists of country names and their id.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 2.0
 * @since version 1.0
 */
public enum Country implements Entity {
    BELARUS(1), RUSSIA(2), POLAND(3), SPAIN(4), ENGLAND(5), UKRAINE(6), USA(7), CHINA(8);

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);
    private long id;

    Country(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        LOGGER.error("Not supported operation");
        throw new NotSupportedOperation();
    }
}