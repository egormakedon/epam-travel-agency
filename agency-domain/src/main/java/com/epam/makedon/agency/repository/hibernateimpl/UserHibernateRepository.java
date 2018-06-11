package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @PersistenceContext
    private EntityManager entityManager;

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
        entityManager.persist(user);
        return true;
    }

    /**
     * @param id to define and find user in repository
     * @return user object, wrapped in optional, which got from repository
     */
    @Override
    public Optional<User> get(long id) {
        final String NATIVE_QUERY_SELECT_USER_BY_ID = "SELECT * FROM user WHERE user_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_USER_BY_ID, User.class);
        query.setParameter(1, id);
        return Optional.ofNullable((User)query.getSingleResult());
    }

    /**
     * @param user object to remove from repository
     * @return boolean result of user removing from repository
     */
    @Override
    public boolean remove(User user) {
        final String NAMED_QUERY_DELETE_USER_BY_ID = "DELETE FROM User WHERE id=:userId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_USER_BY_ID);
        query.setParameter("userId", user.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * @param user object to update in repository
     * @return updated user object, wrapped in optional
     */
    @Override
    public Optional<User> update(User user) {
        return Optional.ofNullable(entityManager.merge(user));
    }
}
