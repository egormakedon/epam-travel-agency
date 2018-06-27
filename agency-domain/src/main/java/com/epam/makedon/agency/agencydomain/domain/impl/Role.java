package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;

/**
 * Enum Role is Entity class.
 * This enum consists of role names and their id.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.domain
 * @since version 6.0
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
                throw new RuntimeException("Illegal id argument - " + id);
        }
    }
}