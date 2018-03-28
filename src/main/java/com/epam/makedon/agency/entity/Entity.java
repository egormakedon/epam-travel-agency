package com.epam.makedon.agency.entity;

import java.io.Serializable;

/**
 * Interface {@code Entity} is the mark, which define com.epam.makedon.agency.entity classes.
 * Extends Serializable and Cloneable
 * Has setter and getter ID.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 1.0
 * @since version 1.0
 */
public interface Entity extends Serializable, Cloneable {
    void setId(long id);
    long getId();
}