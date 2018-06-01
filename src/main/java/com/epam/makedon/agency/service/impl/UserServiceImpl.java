package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.UserService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Class UserServiceImpl implements UserService.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @since version 1.0
 */
@Service
@Profile("service")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Setter
    private UserRepository userRepository;

    public UserServiceImpl() {}

    /**
     * User add method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param user object, which be adding to repository
     * @return boolean, result of adding
     * @throws ServiceException cause param is null or exception of adding
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(User user) {
        if (user == null) {
            LOGGER.error("user argument in add method is null");
            throw new ServiceException("user argument in add method is null");
        }

        try {
            return userRepository.add(user);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * User get method. Supported with @Transactional (Isolation.READ_COMMITTED, Propagation.REQUIRED)
     *
     * @param id to identify and find user in repository
     * @return user object, wrapped in Optional, cause null
     * @throws ServiceException cause exception of getting
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<User> get(long id) {
        if (id < 1) {
            LOGGER.error("id = " + id + " - invalid, id can be >= 1");
            throw new ServiceException("id = " + id + " - invalid, id can be >= 1");
        }

        try {
            return userRepository.get(id);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * User remove method. Supported with @Transactional (Isolation.REPEATABLE_READ, Propagation.REQUIRED)
     *
     * @param user object, which be removing from repository
     * @return boolean result of removing
     * @throws ServiceException cause param is null or exception of removing
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(User user) {
        if (user == null) {
            LOGGER.error("user argument in remove method is null");
            throw new ServiceException("user argument in remove method is null");
        }

        try {
            return userRepository.remove(user);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }

    /**
     * User update method. Supported with @Transactional (Isolation.READ_UNCOMMITTED, Propagation.REQUIRED)
     *
     * @param user object, which be updating in repository
     * @return user object, wrapped in Optional, cause null
     * @throws ServiceException cause param is null or exception of updating
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<User> update(User user) {
        if (user == null) {
            LOGGER.error("user argument in update method is null");
            throw new ServiceException("user argument in update method is null");
        }

        try {
            return userRepository.update(user);
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new ServiceException(e);
        }
    }
}
