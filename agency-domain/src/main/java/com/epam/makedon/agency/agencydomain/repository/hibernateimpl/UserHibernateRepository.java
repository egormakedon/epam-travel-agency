package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Repository class for {@link User} class,
 * using {@link com.epam.makedon.agency.agencydomain.config.MainHibernateConfiguration} class,
 * implements {@link UserRepository} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Repository
@Profile("hibernateRepository")

public class UserHibernateRepository implements UserRepository {

    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public UserHibernateRepository() {}

    /**
     * Add operation
     *
     * @param user {@link User}
     * @return true/false
     */
    @Override
    public boolean add(User user) {
        entityManager.persist(user);
        return true;
    }

    /**
     * Get/find/take operation
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<User> get(long id) {
        final String NATIVE_QUERY_SELECT_USER_BY_ID = "SELECT * " +
                "FROM \"user\" " +
                "WHERE user_id=?";
        Query query = entityManager.createNativeQuery(NATIVE_QUERY_SELECT_USER_BY_ID, User.class);
        query.setParameter(1, id);
        return Optional.ofNullable((User)query.getSingleResult());
    }

    /**
     * Find operation.
     *
     * @param username of User
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<User> findByUsername(String username) {
        final String NAMED_QUERY_SELECT_USER_BY_USERNAME = "FROM User " +
                "WHERE login=:username";
        Query query = entityManager.createQuery(NAMED_QUERY_SELECT_USER_BY_USERNAME);
        query.setParameter(USERNAME, username);
        User user = null;
        try {
            user = (User)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Remove operation
     *
     * @param user {@link User}
     * @return true/false
     */
    @Override
    public boolean remove(User user) {
        final String NAMED_QUERY_DELETE_USER_BY_ID = "DELETE " +
                "FROM User " +
                "WHERE id=:userId";
        Query query = entityManager.createQuery(NAMED_QUERY_DELETE_USER_BY_ID);
        query.setParameter(USER_ID, user.getId());
        return query.executeUpdate() == 1;
    }

    /**
     * Update operation
     *
     * @param user {@link User}
     * @return object, wrapped in {@link Optional} class
     */
    @Override
    public Optional<User> update(User user) {
        return Optional.ofNullable(entityManager.merge(user));
    }
}