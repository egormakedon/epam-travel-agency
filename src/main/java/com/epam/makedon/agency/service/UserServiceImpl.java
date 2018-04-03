package com.epam.makedon.agency.service;

import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean add(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return userRepository.add(user);
    }

    @Override
    public Optional<User> get(long id) {
        return userRepository.get(id);
    }

    @Override
    public boolean remove(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return userRepository.remove(user);
    }

    @Override
    public Optional<User> update(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return userRepository.update(user);
    }
}
