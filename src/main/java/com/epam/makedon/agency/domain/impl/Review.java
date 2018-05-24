package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;

/**
 * Class {@code Review} is Entity class.
 * It store information about users review.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @version 3.0
 * @since version 1.0
 */
@Data
public class Review implements Entity {
    private long id;
    private Tour tour;
    private User user;
    private String content;
}