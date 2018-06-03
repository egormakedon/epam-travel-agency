package com.epam.makedon.agency.domain.impl;

import com.epam.makedon.agency.domain.Entity;
import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

/**
 * Class Review is Entity class.
 * It store information about users review.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.domain
 * @since version 1.0
 */
@Data
//@javax.persistence.Entity(name = "Review")
@Table(name = "review")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Review implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "fk_tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;

    @Column(name = "review_content")
    private String content;

//    @Version
//    private Integer version;
}