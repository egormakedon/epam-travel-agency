package com.epam.makedon.agency.agencydomain.domain.impl;

import com.epam.makedon.agency.agencydomain.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class Review is Entity class.
 * It store information about users review.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.domain
 * @since version 1.0
 */
@Data
@javax.persistence.Entity(name = "Review")
@Table(name = "review")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Review implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    @NotNull
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_tour_id")
    @NotNull
    private Tour tour;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    @NotNull
    private User user;

    @Column(name = "review_content")
    @NotNull
    @Size(min = 2)
    private String content;

    @Version
    @Column(name = "review_version")
    private Integer version;
}