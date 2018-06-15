package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;

/**
 * Enum TourTypeCollectionRepository is Entity class.
 * It stores type of tours, which exists in the project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.domain
 * @since version 1.0
 */
public enum TourType implements Entity {
    CHILDREN(1), WEEKEND(2), WEDDING(3), SHOPING(4), EXCURSION(5);
    private long id;
    TourType(long id) { this.id = id; }
    public long getId() { return id; }
    public static TourType fromCode(long id) {
        switch ((int)id) {
            case 1:
                return CHILDREN;
            case 2:
                return WEEKEND;
            case 3:
                return WEDDING;
            case 4:
                return SHOPING;
            case 5:
                return EXCURSION;
            default:
                throw new RuntimeException("Illegal id argument - " + id);
        }
    }
}