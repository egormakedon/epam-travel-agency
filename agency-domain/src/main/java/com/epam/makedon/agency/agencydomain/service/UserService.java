package com.epam.makedon.agency.agencydomain.service;

import com.epam.makedon.agency.agencydomain.domain.impl.User;

import java.util.Optional;

/**
 * Interface UserService mark service with User domain.
 *
 * @author Yahor Makedon
 * @see com.epam.makedon.agency.agencydomain.service
 * @since version 1.0
 */
public interface UserService extends Service<User> {

    Optional<User> findByUsername(String username);
}