package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;

/**
 * Class {@code Hotel} is Entity class.
 * This class stores information about hotels, which uses in project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @version 4.0
 * @since version 1.0
 */
@Data
public class Hotel implements Entity {
    private long id;
    private String name;
    private String phone;
    private byte stars;
}