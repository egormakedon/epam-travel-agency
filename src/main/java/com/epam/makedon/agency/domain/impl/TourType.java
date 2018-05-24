package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum {@code TourTypeCollectionRepository} is Entity class.
 * It stores type of tours, which exists in the project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @version 2.0
 * @since version 1.0
 */

public enum TourType implements Entity {
    CHILDREN(1), WEEKEND(2), WEDDING(3), SHOPING(4), EXCURSION(5);

    private long id;

    TourType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}