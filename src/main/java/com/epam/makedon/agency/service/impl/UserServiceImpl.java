package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.service
 * @version 3.0
 * @since version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {}

    @Autowired(required = false)
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired(required = false)
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean add(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return userRepository.add(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<User> get(long id) {
        return userRepository.get(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean remove(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return userRepository.remove(user);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<User> update(User user) {
        if (user == null) {
            LOGGER.error("argument is null");
            throw new ServiceException("argument is null");
        }
        return userRepository.update(user);
    }
}
