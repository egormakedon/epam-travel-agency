package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Class Review is Entity class.
 * It store information about users review.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @since version 1.0
 */
@Data
@javax.persistence.Entity(name = "Review")
@Table(name = "review")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Review implements Entity {
    private long id;
    private Tour tour;
    private User user;
    private String content;

    @Version
    private Integer version;
}