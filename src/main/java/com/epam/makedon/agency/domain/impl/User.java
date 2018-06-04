package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @NotNull
    private long id;

    @Column(name = "user_login")
    @NotNull
    @Size(min = 2, max = 20)
    private String login;

    @Column(name = "user_password")
    @NotNull
    @Size(min = 2, max = 20)
    private String password;

    //
    private List<Tour> tourList = new ArrayList<>();

    //
    private List<Review> reviewList = new ArrayList<>();

    @Version
    @Column(name = "user_version")
    private Integer version;
}