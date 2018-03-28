package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.NotSupportedOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum {@code TourType} is Entity class.
 * It stores type of tours, which exists in the project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 2.0
 * @since version 1.0
 */
public enum TourType implements Entity {
    CHILDREN(1), WEEKEND(2), WEDDING(3), SHOPING(4), EXCURSION(5);

    private static final Logger LOGGER = LoggerFactory.getLogger(TourType.class);
    private long id;

    TourType(long id) {
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