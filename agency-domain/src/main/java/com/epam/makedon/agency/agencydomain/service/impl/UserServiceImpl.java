package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.UserRepository;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for {@link User} class,
 * implements {@link UserService} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Service
@Profile("service")

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * default constructor
     */
    public UserServiceImpl() {}

    /**
     * Add operation,
     * supported with {@link Transactional}.
     *
     * @param user {@link User}
     * @return true/false
     * @throws ServiceException cause param is null
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(User user) {
        if (user == null) {
            LOGGER.error("user argument in add method is null");
            throw new ServiceException("user argument in add method is null");
        }

        return userRepository.add(user);
    }

    /**
     * Get/find/take operation,
     * supported with {@link Transactional}.
     *
     * @param id of object
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public Optional<User> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        return userRepository.get(id);
    }

    /**
     * Find operation,
     * supported with {@link Transactional}.
     *
     * @param username of User
     * @return object, wrapped in {@link Optional} class
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    /**
     * Remove operation,
     * supported with {@link Transactional}.
     *
     * @param user {@link User}
     * @return true/false
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(User user) {
        if (user == null) {
            LOGGER.error("user argument in remove method is null");
            throw new ServiceException("user argument in remove method is null");
        }

        return userRepository.remove(user);
    }

    /**
     * Update operation,
     * supported with {@link Transactional}.
     *
     * @param user {@link User}
     * @return object, wrapped in {@link Optional} class
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<User> update(User user) {
        if (user == null) {
            LOGGER.error("user argument in update method is null");
            throw new ServiceException("user argument in update method is null");
        }

        return userRepository.update(user);
    }
}