package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;

/**
 * This entity class describe information about user Role,
 * implements {@link Entity} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

public enum Role implements Entity {
    MEMBER(1), ADMIN(2);
    private long id;
    Role(long id) { this.id = id; }
    public long getId() { return id; }
    public static Role fromCode(long id) {
        switch ((int)id) {
            case 1:
                return MEMBER;
            case 2:
                return ADMIN;
            default:
                throw new IllegalArgumentException("Illegal id argument - " + id);
        }
    }
}