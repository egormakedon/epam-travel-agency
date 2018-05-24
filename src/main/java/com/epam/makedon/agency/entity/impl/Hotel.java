package com.epam.makedon.agency.entity.impl;

import com.epam.makedon.agency.entity.Entity;
import com.epam.makedon.agency.entity.IdCounter;
import lombok.Data;

/**
 * Class {@code Hotel} is Entity class.
 * This class stores information about hotels, which uses in project.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.entity
 * @version 4.0
 * @since version 1.0
 */
@Data
public class Hotel implements Entity {
    private static Long id = 0L;

    private long hotelId;
    private String name;
    private String phone;
    private byte stars;

    @Override
    public void setId(long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public long getId() {
        return hotelId;
    }

}