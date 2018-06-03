package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Table;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

/**
 * Class User is Entity class.
 * It stores user information.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @since version 1.0
 */
@Data
@javax.persistence.Entity(name = "User")
@Table(name = "user")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class User implements Entity {
    private long id;
    private String login;
    private String password;
    private List<Tour> tourList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    @Version
    private Integer version;
}