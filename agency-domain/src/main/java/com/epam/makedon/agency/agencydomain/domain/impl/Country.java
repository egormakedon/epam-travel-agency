package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;

/**
 * This entity class describe information about Country,
 * implements {@link Entity} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public enum Country implements Entity {
    BELARUS(1), RUSSIA(2), POLAND(3), SPAIN(4), ENGLAND(5), UKRAINE(6), USA(7), CHINA(8);
    private long id;
    Country(long id) { this.id = id; }
    public long getId() { return id; }
    public static Country fromCode(long id) {
        switch ((int)id) {
            case 1:
                return BELARUS;
            case 2:
                return RUSSIA;
            case 3:
                return POLAND;
            case 4:
                return SPAIN;
            case 5:
                return ENGLAND;
            case 6:
                return UKRAINE;
            case 7:
                return USA;
            case 8:
                return CHINA;
            default:
                throw new IllegalArgumentException("Illegal id argument - " + id);
        }
    }
}