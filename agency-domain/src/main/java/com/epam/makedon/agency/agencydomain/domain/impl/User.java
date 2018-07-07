package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;
import com.epam.makedon.agency.agencydomain.domain.RoleConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * This entity class describe information about User,
 * implements {@link Entity} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@javax.persistence.Entity(name = "User")
@Table(name = "\"user\"")
@OptimisticLocking(type = OptimisticLockType.VERSION)
@EqualsAndHashCode
@Document(collection = "user")

public class User implements Entity {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @NotNull
    private long id;

    @Setter
    @Getter
    @Column(name = "user_login")
    @NotNull
    @Size(max = 255, min = 1)
    private String login;

    @Setter
    @Getter
    @Column(name = "user_password")
    @NotNull
    @Size(max = 255, min = 1)
    private String password;

    @Setter
    @Getter
    @Transient
    private String confirmPassword;

    @Setter
    @Getter
    @Basic
    @Convert(converter = RoleConverter.class)
    @Column(name = "role_id")
    private Role role = Role.MEMBER;

    @Setter
    @Getter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_tour",
        joinColumns = {@JoinColumn(name = "fk_user_id")},
        inverseJoinColumns = {@JoinColumn(name = "fk_tour_id")})
    @NotNull
    private List<Tour> tourList = new ArrayList<>();

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "review",
            joinColumns = {@JoinColumn(name = "fk_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "review_id")})
    @NotNull
    private List<Review> reviewList = new ArrayList<>();

    @Setter
    @Getter
    @Version
    @Column(name = "user_version")
    private Integer version;

    /**
     * default constructor
     */
    public User() {}
}