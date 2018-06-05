package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class UserHibernateRepository implements UserRepository.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.repository
 * @since version 4.0
 */
@Repository
@Profile("hibernateRepository")
public class UserHibernateRepository implements UserRepository {

    /**
     * default constructor
     */
    public UserHibernateRepository() {}

    /**
     * @param user object, which be insert into repository
     * @return boolean result of inserting
     */
    @Override
    public boolean add(User user) {
        return false;
    }

    /**
     * @param id to define and find user in repository
     * @return user object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    /**
     * @param user object to remove from repository
     * @return boolean result of user removing from repository
     */
    @Override
    public boolean remove(User user) {
        return false;
    }

    /**
     * @param user object to update in repository
     * @return updated user object, wrapped in optional
     */
    @Override
    public Optional<User> update(User user) {
        return Optional.empty();
    }
}
