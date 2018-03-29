package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.impl.UserRepositoryImpl;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 2.0
 * @since version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public boolean add(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return UserRepositoryImpl.getInstance().add(user);
    }

    @Override
    public Optional<User> get(long id) {
        return UserRepositoryImpl.getInstance().get(id);
    }

    @Override
    public boolean remove(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return UserRepositoryImpl.getInstance().remove(user);
    }

    @Override
    public Optional<User> update(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return UserRepositoryImpl.getInstance().update(user);
    }
}
