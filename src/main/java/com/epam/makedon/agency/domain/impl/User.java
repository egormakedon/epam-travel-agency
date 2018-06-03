package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
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
//@javax.persistence.Entity(name = "User")
@Table(name = "user")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class User implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_login")
    private String login;

    @Column(name = "user_password")
    private String password;

    @OneToMany
    @JoinColumn(table = "user_tour", name = "user_id", referencedColumnName = "fk_user_id")
    @JoinColumn(table = "tour", name = "fk_tour_id", referencedColumnName = "tour_id")
    private List<Tour> tourList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "fk_user_id", table = "review")
    private List<Review> reviewList = new ArrayList<>();

//    @Version
//    private Integer version;
}